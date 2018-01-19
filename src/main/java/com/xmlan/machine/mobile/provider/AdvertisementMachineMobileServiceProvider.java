package com.xmlan.machine.mobile.provider;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.config.Global;
import com.xmlan.machine.common.util.PushUtils;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.advertisementMachine.service.MachineSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayakurayuki on 2018/1/9-14:44.
 * Package: com.xmlan.machine.mobile.provider
 */
@Controller
@RequestMapping("${mobilePath}/machine")
public class AdvertisementMachineMobileServiceProvider extends BaseController {

    @Autowired
    private AdvertisementMachineService service;
    @Autowired
    private MachineSensorService machineSensorService;

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
     *
     * @param id    广告机ID
     * @param token 用户鉴权用的token
     * @return 广告机对象信息，token验证通过则返回对象，不通过则返回null
     */
    @RequestMapping(value = "/get/{id}/{token}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public AdvertisementMachine get(@PathVariable String id, @PathVariable("token") String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        return service.get(id);
    }

    /**
     * 通过userID查询用户拥有的广告机列表
     *
     * @param userID 用户ID
     * @param token  用户鉴权用的token
     * @return 广告机列表，token验证通过则返回列表，不通过则返回null
     */
    @RequestMapping(value = "/find/{userID}/{token}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<AdvertisementMachine> find(@PathVariable int userID, @PathVariable("token") String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        AdvertisementMachine machine = new AdvertisementMachine();
        machine.setUserID(userID);
        return AdvertisementMachineService.findForProvider(machine);
    }

    /**
     * 通过最大经纬度和最小经纬度查询区域内的广告机
     *
     * @param minLongitude
     * @param maxLongitude
     * @param minLatitude
     * @param maxLatitude
     * @param token
     * @return
     */
    @RequestMapping(value = "/position/query/{token}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<AdvertisementMachine> positionQuery(String minLongitude, String maxLongitude, String minLatitude, String maxLatitude, @PathVariable("token") String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        if (isNotBlank(minLongitude, maxLongitude, minLatitude, maxLatitude)) {
            return AdvertisementMachineService.positionQuery(minLongitude, maxLongitude, minLatitude, maxLatitude);
        }
        return null;
    }

    @RequestMapping(value = "/light/{id}/{operate}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map light(@PathVariable("id") int id, @PathVariable("operate") int operate, String token) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put("responseCode", FAILURE);
            map.put("message", "身份校验失败");
            return map;
        }
        int responseCode = service.lightControl(id, operate);
        map.put("responseCode", responseCode);
        if (responseCode == NO_SUCH_ROW) {
            map.put("message", "目标路灯不存在");
        } else if (responseCode == ERROR_REQUEST) {
            map.put("message", "操作码不正确");
        } else if (responseCode == DONE) {
            map.put("message", operate == 1 ? "开灯！" : "关灯！");
            HashMap<String, Integer> command = Maps.newHashMap();
            command.put("id", id);
            command.put("operate", operate);
            command.put("type", TYPE_LIGHT);
            JPushClient pushClient = new JPushClient(Global.getMasterSecret(), Global.getAppKey(), null, ClientConfig.getInstance());
            PushPayload payload = PushUtils.buildPayload(String.valueOf(id), "Light switch.", command);
            try {
                PushResult result = pushClient.sendPush(payload);
                logger.trace(result);
            } catch (APIRequestException | APIConnectionException e) {
                logger.error("API exception with: " + e.getMessage());
            }
        } else {
            map.put("message", "系统繁忙");
        }
        return map;
    }

    @RequestMapping(value = "/charge/{id}/{operate}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map charge(@PathVariable("id") int id, @PathVariable("operate") int operate, String token) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!TokenUtils.validateToken(token)) {
            map.put("responseCode", FAILURE);
            map.put("message", "身份校验失败");
            return map;
        }
        int responseCode = service.chargeControl(id, operate);
        map.put("responseCode", responseCode);
        if (responseCode == NO_SUCH_ROW) {
            map.put("message", "目标充电桩不存在");
        } else if (responseCode == ERROR_REQUEST) {
            map.put("message", "操作码不正确");
        } else if (responseCode == DONE) {
            map.put("message", operate == 1 ? "正在充电！" : "充电结束，闲置中。");
            HashMap<String, Integer> command = Maps.newHashMap();
            command.put("id", id);
            command.put("operate", operate + 2);
            command.put("type", TYPE_CHARGE);
            JPushClient pushClient = new JPushClient(Global.getMasterSecret(), Global.getAppKey(), null, ClientConfig.getInstance());
            PushPayload payload = PushUtils.buildPayload(String.valueOf(id), "Charge switch.", command);
            try {
                PushResult result = pushClient.sendPush(payload);
                logger.trace(result);
            } catch (APIRequestException | APIConnectionException e) {
                logger.error("API exception with: " + e.getMessage());
            }
        } else {
            map.put("message", "系统繁忙");
        }
        return map;
    }

    @RequestMapping(value = "/environment/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public MachineSensor currentEnvironmentStatus(@PathVariable("id") int id, String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        return machineSensorService.getByMachineID(id);
    }

}
