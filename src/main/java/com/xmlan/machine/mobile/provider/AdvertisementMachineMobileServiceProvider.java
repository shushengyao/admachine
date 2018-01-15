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
import com.xmlan.machine.common.util.JsonUtils;
import com.xmlan.machine.common.util.PushUtils;
import com.xmlan.machine.common.util.StringUtils;
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
@RequestMapping("${mobilePath}/advertisementMachine")
public class AdvertisementMachineMobileServiceProvider extends BaseController {

    @Autowired
    private AdvertisementMachineService service;
    @Autowired
    private MachineSensorService machineSensorService;

    private static boolean isNotBlank(String... args) {
        for (String string : args) {
            if (StringUtils.isBlank(string)) {
                return false;
            }
        }
        return true;
    }

    @RequestMapping(value = "/get/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public AdvertisementMachine get(@PathVariable String id) {
        return service.get(id);
    }

    @RequestMapping(value = "/find/{userID}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<AdvertisementMachine> find(@PathVariable int userID, String token) {
        AdvertisementMachine machine = new AdvertisementMachine();
        machine.setUserID(userID);
        return AdvertisementMachineService.findForProvider(machine);
    }

    @RequestMapping(value = "/position/query", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<AdvertisementMachine> positionQuery(String minLongitude, String maxLongitude, String minLatitude, String maxLatitude) {
        if (isNotBlank(minLongitude, maxLongitude, minLatitude, maxLatitude)) {
            return AdvertisementMachineService.positionQuery(minLongitude, maxLongitude, minLatitude, maxLatitude);
        }
        return null;
    }

    @RequestMapping(value = "/light", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map light(final int id, int operate) {
        int responseCode = service.lightControl(id, operate);
        HashMap<String, Object> map = Maps.newHashMap();
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
            JPushClient pushClient = new JPushClient(Global.getMasterSecret(), Global.getAppKey(), null, ClientConfig.getInstance());
            PushPayload payload = PushUtils.buildPayload(String.valueOf(id), "Light switch.", JsonUtils.toJsonString(command));
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

    @RequestMapping(value = "/environment/status/{machineID}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public MachineSensor currentEnvironmentStatus(@PathVariable("machineID") String machineID) {
        return machineSensorService.getByMachineID(machineID);
    }

}
