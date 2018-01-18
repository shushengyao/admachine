package com.xmlan.machine.mobile.provider;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementCache;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.config.Global;
import com.xmlan.machine.common.util.JsonUtils;
import com.xmlan.machine.common.util.PushUtils;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.advertisement.entity.Advertisement;
import com.xmlan.machine.module.advertisement.service.AdvertisementService;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayakurayuki on 2018/1/17-18:41. <br/>
 * Package: com.xmlan.machine.mobile.provider <br/>
 */
@Controller
@RequestMapping("${mobilePath}/ad")
public class AdvertisementMobileServiceProvider extends BaseController {

    @Autowired
    private AdvertisementService advertisementService;

    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Advertisement get(int id, String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        return advertisementService.get(StringUtils.EMPTY + id);
    }

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

    @RequestMapping(value = "/uploadMedia", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map uploadMedia(int id, String token, MultipartFile file) {
        if (!TokenUtils.validateToken(token)) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("responseCode", FAILURE);
            map.put("message", "身份校验失败");
            return map;
        }
        int responseCode = advertisementService.uploadMedia(StringUtils.EMPTY + id, file);
        return push(id, responseCode, "新广告媒体");
    }

    @RequestMapping(value = "/manualPush", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map manualPush(int id, String token) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put("responseCode", FAILURE);
            map.put("message", "身份校验失败");
            return map;
        }
        return push(id, DEFAULT, "手动推送");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map delete(int id, String token) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put("responseCode", FAILURE);
            map.put("message", "身份校验失败");
            return map;
        }
        Advertisement advertisement = AdvertisementCache.get(id);
        int responseCode = advertisementService.delete(advertisement);
        map.put("responseCode", responseCode);
        if (responseCode == 0) {
            map.put("message", "没有对应的广告");
        } else {
            map.put("responseCode", DONE);
            map.put("message", "删除成功");
        }
        return map;
    }

    /**
     * 推送封装方法
     *
     * @param id 广告ID
     * @return 处理结果数据
     */
    private Map push(int id, int responseCode, String message) {
        // 获取广告对象
        Advertisement advertisement = AdvertisementCache.get(id);
        // 获取广告机对象
        AdvertisementMachine machine = AdvertisementMachineCache.get(advertisement.getMachineID());
        HashMap<String, Integer> pushData = Maps.newHashMap();      // 封装推送体数据
        pushData.put("advertisementID", advertisement.getId());     // 添加推送体数据内容（广告ID）
        JPushClient client = new JPushClient(                       // 创建推送客户端
                Global.getMasterSecret(), Global.getAppKey(), null, ClientConfig.getInstance()
        );
        PushPayload payload = PushUtils.buildPayload(               // 创建预处理推送体
                StringUtils.EMPTY + machine.getId(), message, JsonUtils.toJsonString(pushData)
        );
        PushResult result;
        HashMap<String, Object> map = Maps.newHashMap();
        try {
            result = client.sendPush(payload);
            if (responseCode == DEFAULT) {
                map.put("responseCode", result.statusCode);
            } else {
                map.put("responseCode", responseCode);
            }
            map.put("message", message);
            logger.trace(result.getOriginalContent());
        } catch (APIConnectionException | APIRequestException e) {
            logger.error("API exception with: " + e.getMessage());
        }
        return map;
    }

}
