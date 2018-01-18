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
        advertisementMachine.setName(StringUtils.EMPTY);
        advertisementMachine.setUserID(-2);
        advertisementMachine.setAddress(StringUtils.EMPTY);
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

    @RequestMapping(value = "/environment/status/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public MachineSensor currentEnvironmentStatus(@PathVariable("id") int id) {
        return machineSensorService.getByMachineID(id);
    }

    @RequestMapping(value = "/environment/update/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> updateEnvironmentStatus(@PathVariable("id") int id, String temperature, String humidity, String pm25, String pm10) {
        MachineSensor data = machineSensorService.getByMachineID(id);
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
