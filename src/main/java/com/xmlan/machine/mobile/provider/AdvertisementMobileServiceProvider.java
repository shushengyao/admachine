package com.xmlan.machine.mobile.provider;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.*;
import com.xmlan.machine.common.cache.AdvertisementCache;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.config.Global;
import com.xmlan.machine.common.util.*;
import com.xmlan.machine.module.advertisement.entity.Advertisement;
import com.xmlan.machine.module.advertisement.service.AdvertisementService;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.system.service.SysLogService;
import com.xmlan.machine.module.user.entity.User;
import com.xmlan.machine.module.xixun.controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手机端 广告服务接口
 * <p>
 * Package: com.xmlan.machine.mobile.provider
 *
 * @author ayakurayuki
 * @date 2018/1/17-18:41
 */
@Controller
@RequestMapping("${mobilePath}/ad")
public class AdvertisementMobileServiceProvider extends BaseController {

    private final AdvertisementService advertisementService;
    private final SysLogService sysLogService;
    private final ThreadPoolTaskExecutor taskExecutor;

    /**
     * 构造器注入
     *
     * @param advertisementService 广告Service
     * @param sysLogService        系统日志Service
     * @param taskExecutor         线程池
     */
    @Autowired
    public AdvertisementMobileServiceProvider(
            AdvertisementService advertisementService,
            SysLogService sysLogService,
            ThreadPoolTaskExecutor taskExecutor) {
        this.advertisementService = advertisementService;
        this.sysLogService = sysLogService;
        this.taskExecutor = taskExecutor;
    }

    /**
     * 获取广告对象
     * <p>
     * URL: /mob/ad/get
     * <p>
     * Method: Post
     *
     * @param id    int 广告ID
     * @param token String token身份验证
     * @return 广告对象
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Advertisement get(int id, String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        return advertisementService.get(String.valueOf(id));
    }

    /**
     * 通过广告机ID获取广告列表
     * <p>
     * URL: /mob/ad/find
     * <p>
     * Method: Post
     *
     * @param machineID int 广告机ID
     * @param token     String token身份验证
     * @return 广告列表
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<Advertisement> find(int machineID, String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        Advertisement advertisement = new Advertisement();
        advertisement.setMachineID(machineID);
        return advertisementService.findList(advertisement);
    }

    /**
     * 新增广告
     * <p>
     * URL: /mob/ad/newAD
     * <p>
     * Method: Post
     *
     * @param machineID int 广告机ID
     * @param name      String 广告名称
     * @param time      int 播放时间（秒）
     * @param token     String token身份验证
     * @param file      File 媒体文件
     * @return 推送结果
     */
    @RequestMapping(value = "/newAD", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map insertAD(int machineID, String name, int time, String token, MultipartFile file) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put(keyResponseCode, FAILURE);
            map.put(keyMessage, "身份校验失败");
            return map;
        }
        User user = TokenUtils.validateTokenGetUser(token);
        Advertisement advertisement = new Advertisement();
        advertisement.setName(name);
        advertisement.setTime(time);
        advertisement.setUserID(user.getId());
        advertisement.setMachineID(machineID);
        advertisement.setAddTime(DateUtils.getDateTime());
        advertisementService.insert(advertisement);
        taskExecutor.execute(() ->
                sysLogService.log(
                        ModuleEnum.Advertisement,
                        OperateEnum.New,
                        user.getId(),
                        ObjectEnum.User,
                        "在手机上新增了一条广告"
                )
        );
        int id = AdvertisementCache.getNewInserted(advertisement).getId();
        return uploadMedia(id, time, token, file);
    }

    /**
     * 更新广告，上传媒体文件，并向广告机发送推送
     * <p>
     * URL: /mob/ad/uploadMedia
     * <p>
     * Method: Post
     * @param id    int 广告ID
     * @param time  int 播放时间（秒）
     * @param token String token身份验证
     * @param file  File 文件
     * @return 更新结果
     */
    @RequestMapping(value = "/uploadMedia", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map uploadMedia(int id, int time, String token, MultipartFile file) {
        if (!TokenUtils.validateToken(token)) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put(keyResponseCode, FAILURE);
            map.put(keyMessage, "身份校验失败");
            return map;
        }
        int responseCode = advertisementService.uploadMedia(String.valueOf(id), time, file);
        return pushUpdate(id, responseCode, "新广告");
    }

    /**
     * 手机端上传led广告
     * @param id led的id
     * @param led led的编号
     * @param token 验证
     * @param file 文件
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadLed", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map uploadLed(int id,String led,String token,MultipartFile file) throws IOException {
        Date date = new Date();
        String dataForm = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date);
        if (!TokenUtils.validateToken(token)) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put(keyResponseCode, FAILURE);
            map.put(keyMessage, "身份校验失败");
            return map;
        }
        int indexb = 0;
        int responseCode = -3;
        String oldName = file.getOriginalFilename();
        int index = oldName.lastIndexOf(".");
        String type = oldName.substring(index);
        String fileName = UploadUtils.saveFile(dataForm,file, BaseBean.path);
        if (type.equals(".mp4")){
            mp4(responseCode,fileName,led);
        } else if (type.equals(".png") || type.equals(".jpg") || type.equals(".jpeg") || type.equals(".gif")) {
            String filenameTemp= BaseBean.path+"demo.html";
            String call = BaseBean.XWALKURL+dataForm+".html";
            File filename = new File(filenameTemp);
            if (filename.exists()) {
//            filename.createNewFile();
                if (filename.delete()){
                    filename.createNewFile();
                }
            }
            boolean bea= FileUtils.writeToFile("<head><style>body{margin:0;padding:0;}</style></head><body><img src=\""+fileName+"\" style=\"width: 128px;height: 256px\"/></head>",filenameTemp);
            if (bea == true){
                XixunAD xixunAD = new XixunAD();
                xixunAD.clear(led);
                util(responseCode,call,led);
            }else {
                responseCode = BaseBean.FAILURE;
            }
        }
        return pushUpdate(id, responseCode, "新广告");
    }
    private int util(int responseCode,String call,String led){
        LoadUrl loadUrl =new  LoadUrl();
        loadUrl.loadUrl(led);
        responseCode = BaseBean.DONE;
        return responseCode;
    }
    private int mp4(int responseCode,String fileName,String led) throws IOException{
        DownloadFileToLocal downloadFileToLocal = new DownloadFileToLocal();
        downloadFileToLocal.DownloadFileToLocal(fileName,led);
        setPlayList(led,fileName);
        return responseCode;
    }
    public  void setPlayList(String led,String fileName) throws IOException{
        SetPlayList setPlayList = new SetPlayList();
        setPlayList.setPlayList(led,fileName);
        XixunAD xixunAD = new XixunAD();
        xixunAD.clear(led);
    }


    /**
     * 手动推送通知广告机需要更新列表
     * <p>
     * URL: /mob/ad/manualUpdate
     * <p>
     * Method: Post
     *
     * @param machineID int 广告机ID
     * @param token     String token身份验证
     * @return 推送结果
     */
    @RequestMapping(value = "/manualUpdate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> manualUpdate(int machineID, String token) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put(keyResponseCode, FAILURE);
            map.put(keyMessage, "身份校验失败");
            return map;
        }
        // 获取广告机
        AdvertisementMachine machine = AdvertisementMachineCache.get(machineID);

        HashMap<String, Integer> pushData = Maps.newHashMap();
        pushData.put("type", TYPE_MEDIA_UPDATE);
        pushData.put("needUpdate", YES);
        // 创建推送客户端
        JPushClient client = new JPushClient(Global.getMasterSecret(), Global.getAppKey(), null, ClientConfig.getInstance());
        // 创建预处理推送体
        PushPayload payload = PushUtils.buildPayload(String.valueOf(machine.getId()), "广告列表需要更新", pushData);
        PushResult result;
        try {
            result = client.sendPush(payload);
            map.put(keyResponseCode, result.statusCode);
            map.put(keyMessage, "手动推送更新");
            taskExecutor.execute(() ->
                    sysLogService.log(
                            ModuleEnum.Advertisement,
                            OperateEnum.Update,
                            TokenUtils.validateTokenGetUser(token).getId(),
                            ObjectEnum.User,
                            "在手机上手动推送更新"
                    )
            );
            logger.trace(result.getOriginalContent());
        } catch (APIConnectionException e) {
            map.put(keyResponseCode, ERROR_API_CONNECTION_EXCEPTION);
            map.put(keyMessage, "Push connect error.");
            logger.error("API exception with: " + e.getMessage());
        } catch (APIRequestException e) {
            map.put(keyResponseCode, ERROR_API_REQUEST_EXCEPTION);
            map.put(keyMessage, "Push request error.");
            logger.error("API exception with: " + e.getMessage());
        }
        return map;
    }

    /**
     * 手动推送通知广告机有更新
     * <p>
     * URL: /mob/ad/manualPush
     * <p>
     * Method: Post
     *
     * @param id    int 广告ID
     * @param token String token身份验证
     * @return 推送结果
     */
    @RequestMapping(value = "/manualPush", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> manualPush(int id, String token) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put(keyResponseCode, FAILURE);
            map.put(keyMessage, "身份校验失败");
            return map;
        }
        return pushUpdate(id, DEFAULT, "手动推送");
    }

    /**
     * 删除广告
     * <p>
     * URL: /mob/ad/delete
     * <p>
     * Method: Post
     *
     * @param id    int 广告ID
     * @param token String token身份验证
     * @return 更新结果
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map delete(int id, String token) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put(keyResponseCode, FAILURE);
            map.put(keyMessage, "身份校验失败");
            return map;
        }
        Advertisement advertisement = AdvertisementCache.get(id);
        pushUpdate(id, DONE, "有广告被删除了，需要刷新");
        int responseCode = advertisementService.delete(advertisement);
        map.put(keyResponseCode, responseCode);
        if (responseCode != DONE) {
            map.put(keyMessage, "没有对应的广告");
        } else {
            map.put(keyResponseCode, DONE);
            map.put(keyMessage, "删除成功");
        }
        return map;
    }

    /**
     * 推送封装方法
     *
     * @param id 广告ID
     * @return 处理结果数据
     */
    private HashMap<String, Object> pushUpdate(int id, int responseCode, String message) {
        // 获取广告对象
        Advertisement advertisement = AdvertisementCache.get(id);
        // 获取广告机对象
        AdvertisementMachine machine = AdvertisementMachineCache.get(advertisement.getMachineID());
        // 封装推送体数据
        HashMap<String, Integer> pushData = Maps.newHashMap();
        // 添加推送体数据内容（广告ID）
        pushData.put("advertisementID", advertisement.getId());
        pushData.put("type", TYPE_MEDIA_UPDATE);
        pushData.put("needUpdate", YES);
        // 创建推送客户端
        JPushClient client = new JPushClient(Global.getMasterSecret(), Global.getAppKey(), null, ClientConfig.getInstance());
        // 创建预处理推送体
        PushPayload payload = PushUtils.buildPayload(String.valueOf(machine.getId()), message, pushData);
        PushResult result;
        HashMap<String, Object> map = Maps.newHashMap();
        try {
            result = client.sendPush(payload);
            if (responseCode == DEFAULT) {
                map.put(keyResponseCode, result.statusCode);
            } else {
                map.put(keyResponseCode, responseCode);
            }
            map.put(keyMessage, message);
            taskExecutor.execute(() ->
                    sysLogService.log(
                            ModuleEnum.Advertisement,
                            OperateEnum.Push,
                            machine.getUserID(),
                            ObjectEnum.User,
                            message
                    )
            );
            logger.trace(result.getOriginalContent());
        } catch (APIConnectionException e) {
            map.put(keyResponseCode, ERROR_API_CONNECTION_EXCEPTION);
            map.put(keyMessage, "Push connect error.");
            logger.error("API exception with: " + e.getMessage());
        } catch (APIRequestException e) {
            map.put(keyResponseCode, ERROR_API_REQUEST_EXCEPTION);
            map.put(keyMessage, "Push request error.");
            logger.error("API exception with: " + e.getMessage());
        }
        return map;
    }

}
