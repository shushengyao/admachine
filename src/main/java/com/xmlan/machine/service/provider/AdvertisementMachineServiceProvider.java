package com.xmlan.machine.service.provider;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.util.DateUtils;
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
import java.util.Map;

/**
 * Created by ayakurayuki on 2018/1/8-15:43.
 * Package: com.xmlan.machine.service.provider
 */
@Controller
@RequestMapping("${servicePath}/advertisementMachine")
public class AdvertisementMachineServiceProvider extends BaseController {

    @Autowired
    private AdvertisementMachineService service;
    @Autowired
    private MachineSensorService machineSensorService;

    @RequestMapping(value = "/get/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public AdvertisementMachine get(@PathVariable int id) {
        return AdvertisementMachineCache.get(id);
    }

    @RequestMapping(value = "/register", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap register(AdvertisementMachine advertisementMachine) {
        // 程序判断是否有存在的条目
        AdvertisementMachine temp = AdvertisementMachineCache.get(advertisementMachine.getCodeNumber());
        if (temp != null) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("responseCode", DATABASE_DO_NOTHING);
            map.put("errorMessage", "Exists machine with the same code number.");
            return map;
        }
        if (StringUtils.isBlank(advertisementMachine.getAddTime())) {
            advertisementMachine.setAddTime(DateUtils.GetDateTime());
        }
        advertisementMachine.setUserID(-2);
        advertisementMachine.setLongitude("0.0");
        advertisementMachine.setLatitude("0.0");
        advertisementMachine.setLight(0);
        advertisementMachine.setCharge(0);
        advertisementMachine.setChecked(0);
        int result = service.insert(advertisementMachine);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("responseCode", result);
        map.put("id", service.getByCodeNumber(advertisementMachine.getCodeNumber()).getId());
        return map;
    }

    @RequestMapping(value = "/charge", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map charge(int id, int operate) {
        int responseCode = service.chargeControl(id, operate);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("responseCode", responseCode);
        if (responseCode == NO_SUCH_ROW) {
            map.put("message", "目标充电桩不存在");
        } else if (responseCode == ERROR_REQUEST) {
            map.put("message", "操作码不正确");
        } else if (responseCode == DONE) {
            map.put("message", operate == 1 ? "正在充电！" : "充电结束，闲置中。");
        } else {
            map.put("message", "系统繁忙");
        }
        return map;
    }

    @RequestMapping(value = "/environment/status/{machineID}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public MachineSensor currentEnvironmentStatus(@PathVariable("machineID") int machineID) {
        return machineSensorService.getByMachineID(machineID);
    }

    @RequestMapping(value = "/environment/update/{machineID}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> updateEnvironmentStatus(@PathVariable("machineID") int machineID, String temperature, String humidity, String pm25, String pm10) {
        MachineSensor data = machineSensorService.getByMachineID(machineID);
        data.setTemperature(temperature);
        data.setHumidity(humidity);
        data.setPm25(pm25);
        data.setPm10(pm10);
        HashMap<String, Object> info = Maps.newHashMap();
        info.put("id", data.getId());
        info.put("response", machineSensorService.update(data));
        return info;
    }

}
