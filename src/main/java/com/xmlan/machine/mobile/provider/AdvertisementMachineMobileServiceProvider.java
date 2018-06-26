package com.xmlan.machine.mobile.provider;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.base.ModuleEnum;
import com.xmlan.machine.common.base.ObjectEnum;
import com.xmlan.machine.common.base.OperateEnum;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.config.Global;
import com.xmlan.machine.common.util.PushUtils;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手机端 广告机服务接口
 * <p>
 * Package: com.xmlan.machine.mobile.provider
 *
 * @author ayakurayuki
 * @date 2018/1/9-14:44
 */
@Controller
@RequestMapping("${mobilePath}/machine")
public class AdvertisementMachineMobileServiceProvider extends BaseController {

    private final AdvertisementMachineService service;
    private final SysLogService sysLogService;
    private final ThreadPoolTaskExecutor taskExecutor;

    /**
     * 构造器注入
     *
     * @param service       广告机Service
     * @param sysLogService 系统日志Service
     * @param taskExecutor  线程池
     */
    @Autowired
    public AdvertisementMachineMobileServiceProvider(
            AdvertisementMachineService service,
            SysLogService sysLogService,
            ThreadPoolTaskExecutor taskExecutor) {
        this.service = service;
        this.sysLogService = sysLogService;
        this.taskExecutor = taskExecutor;
    }

    /**
     * 判断传来的不定数量的参数是否都不为空
     *
     * @param args 不定长数组，数据类型为java.lang.String
     * @return 数组内参数都不为空，返回true，否则返回false
     */
    private static boolean isNotBlank(String... args) {
        for (String string : args) {
            if (StringUtils.isBlank(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 通过ID获取广告机对象信息
     * <p>
     * URL: /mob/machine/get
     * <p>
     * Method: Post
     *
     * @param id    广告机ID
     * @param token 用户鉴权用的token
     * @return 广告机对象信息，token验证通过则返回对象，不通过则返回null
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public AdvertisementMachine get(String id, String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        return service.get(id);
    }

    /**
     * 通过userID查询用户拥有的广告机列表
     * <p>
     * URL: /mob/machine/find
     * <p>
     * Method: Post
     *
     * @param userID 用户ID
     * @param token  用户鉴权用的token
     * @return 广告机列表，token验证通过则返回列表，不通过则返回null
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<AdvertisementMachine> find(int userID, String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        AdvertisementMachine machine = new AdvertisementMachine();
        machine.setUserID(userID);
        return AdvertisementMachineService.findForProvider(machine);
    }
    /*
        手机端获取accessToken
     */
    @RequestMapping(value = "/getAccessToken",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,String> getAccessToken(){
        String token =service.get("34").getAccessToken();
        Map<String,String> result = new HashMap<>();
        result.put("accessToken",token);
        return result;
    }

    /**
     * 根据摄像头序列号获取设备验证码
     *
     * @return
     */
    @RequestMapping(value = "/getCameraVerificationCode",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,String> getCameraVerificationCode(String cameraSequence){
        Map<String,String> result = new HashMap<>();
        AdvertisementMachine advertisementMachine =service.findIdByCameraSequence(cameraSequence);
        String cameraVerificationCode =advertisementMachine.getCameraVerificationCode();
        result.put("cameraVerificationCode",cameraVerificationCode);
        return result;
    }

    /**
     * 通过最大经纬度和最小经纬度查询区域内的广告机
     * <p>
     * URL: /mob/machine/position/query
     * <p>
     * Method: Post
     *
     * @param minLongitude String 最小经度
     * @param maxLongitude String 最大经度
     * @param minLatitude  String 最小纬度
     * @param maxLatitude  String 最大纬度
     * @param token        String token身份验证
     * @return 查询列表
     */
    @RequestMapping(value = "/position/query", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<AdvertisementMachine> positionQuery(String token, String minLongitude, String maxLongitude, String minLatitude, String maxLatitude) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        if (isNotBlank(minLongitude, maxLongitude, minLatitude, maxLatitude)) {
            return AdvertisementMachineService.positionQuery(minLongitude, maxLongitude, minLatitude, maxLatitude);
        }
        return null;
    }

    /**
     * 开关灯
     * <p>
     * URL: /mob/machine/light/{id}/{operate}
     * <p>
     * Method: Get/Post
     *
     * @param id      int 广告机ID
     * @param operate int 操作码：0/1
     * @param token   String token身份验证
     * @return 操作结果
     */
    @RequestMapping(value = "/light/{id}/{operate}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> light(@PathVariable("id") int id, @PathVariable("operate") int operate, String token) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put(keyResponseCode, FAILURE);
            map.put(keyMessage, "身份校验失败");
            return map;
        }
        int responseCode = service.lightControl(id, operate);
        map.put(keyResponseCode, responseCode);
        if (responseCode == NO_SUCH_ROW) {
            map.put(keyMessage, "目标路灯不存在");
        } else if (responseCode == ERROR_REQUEST) {
            map.put(keyMessage, "操作码不正确");
        } else if (responseCode == DONE) {
            HashMap<String, Integer> command = Maps.newHashMap();
            command.put("id", id);
            command.put("operate", operate);
            command.put("type", TYPE_LIGHT);
            JPushClient pushClient = new JPushClient(Global.getMasterSecret(), Global.getAppKey(), null, ClientConfig.getInstance());
            PushPayload payload = PushUtils.buildPayload(String.valueOf(id), "Light switch.", command);
            try {
                map.put(keyMessage, operate == 1 ? "开灯！" : "关灯！");
                taskExecutor.execute(() -> {
                    if (operate == 1) {
                        sysLogService.log(
                                ModuleEnum.Machine,
                                OperateEnum.Push,
                                TokenUtils.validateTokenGetUser(token).getId(),
                                ObjectEnum.User,
                                "打开了" + AdvertisementMachineCache.getMachineNameByID(id) + "的灯"
                        );
                    } else {
                        sysLogService.log(
                                ModuleEnum.Machine,
                                OperateEnum.Push,
                                TokenUtils.validateTokenGetUser(token).getId(),
                                ObjectEnum.User,
                                "关闭了" + AdvertisementMachineCache.getMachineNameByID(id) + "的灯"
                        );
                    }
                });
                PushResult result = pushClient.sendPush(payload);
                logger.trace(result);
            } catch (APIRequestException e) {
                map.put(keyMessage, "Push request error.");
                map.put(keyResponseCode, ERROR_API_REQUEST_EXCEPTION);
                logger.error("API exception with: " + e.getMessage());
            } catch (APIConnectionException e) {
                map.put(keyMessage, "Push connect error.");
                map.put(keyResponseCode, ERROR_API_CONNECTION_EXCEPTION);
                logger.error("API exception with: " + e.getMessage());
            }
        } else {
            map.put(keyResponseCode, PASS);
            map.put(keyMessage, "系统繁忙");
        }
        return map;
    }

    /**
     * 充电开关
     * <p>
     * URL: /mob/machine/light/{id}/{operate}
     * <p>
     * Method: Get/Post
     *
     * @param id      int 广告机ID
     * @param operate int 操作码：0/1
     * @param token   String token身份验证
     * @return 操作结果
     */
    @RequestMapping(value = "/charge/{id}/{operate}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> charge(@PathVariable("id") int id, @PathVariable("operate") int operate, String token) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put(keyResponseCode, FAILURE);
            map.put(keyMessage, "身份校验失败");
            return map;
        }
        int responseCode = service.chargeControl(id, operate);
        map.put(keyResponseCode, responseCode);
        if (responseCode == NO_SUCH_ROW) {
            map.put(keyMessage, "目标充电桩不存在");
        } else if (responseCode == ERROR_REQUEST) {
            map.put(keyMessage, "操作码不正确");
        } else if (responseCode == DONE) {
            HashMap<String, Integer> command = Maps.newHashMap();
            command.put("id", id);
            command.put("operate", operate + 2);
            command.put("type", TYPE_CHARGE);
            JPushClient pushClient = new JPushClient(Global.getMasterSecret(), Global.getAppKey(), null, ClientConfig.getInstance());
            PushPayload payload = PushUtils.buildPayload(String.valueOf(id), "Charge switch.", command);
            try {
                map.put(keyMessage, operate == 1 ? "正在充电！" : "充电结束，闲置中。");
                taskExecutor.execute(() -> {
                    if (operate == 1) {
                        sysLogService.log(
                                ModuleEnum.Machine,
                                OperateEnum.Push,
                                TokenUtils.validateTokenGetUser(token).getId(),
                                ObjectEnum.User,
                                "启动了" + AdvertisementMachineCache.getMachineNameByID(id) + "的充电功能"
                        );
                    } else {
                        sysLogService.log(
                                ModuleEnum.Machine,
                                OperateEnum.Push,
                                TokenUtils.validateTokenGetUser(token).getId(),
                                ObjectEnum.User,
                                "结束了" + AdvertisementMachineCache.getMachineNameByID(id) + "的充电功能，结束充电"
                        );
                    }
                });
                PushResult result = pushClient.sendPush(payload);
                logger.trace(result);
            } catch (APIRequestException e) {
                map.put(keyMessage, "Push request error.");
                map.put(keyResponseCode, ERROR_API_REQUEST_EXCEPTION);
                logger.error("API exception with: " + e.getMessage());
            } catch (APIConnectionException e) {
                map.put(keyMessage, "Push connect error.");
                map.put(keyResponseCode, ERROR_API_CONNECTION_EXCEPTION);
                logger.error("API exception with: " + e.getMessage());
            }
        } else {
            map.put(keyMessage, "系统繁忙");
        }
        return map;
    }

}
