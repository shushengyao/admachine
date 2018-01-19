package com.xmlan.machine.service.provider;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor;
import com.xmlan.machine.module.advertisementMachine.service.MachineSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by ayakurayuki on 2018/1/19-14:14. <br/>
 * Package: com.xmlan.machine.service.provider <br/>
 */
@Controller
@RequestMapping("${servicePath}/sensor")
class SensorServiceProvider extends BaseController {

    @Autowired
    private MachineSensorService sensorService;

    /**
     * 获取广告机传感器天气数据
     * <p>
     * URL: /serv/sensor/status
     * <p>
     * Method: POST
     *
     * @param id int | 广告机ID
     * @return MachineSensor | 天气数据对象
     */
    @RequestMapping(value = "/status", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public MachineSensor current(int id) {
        return sensorService.getByMachineID(id);
    }

    /**
     * 更新广告机传感器天气数据
     * <p>
     * URL: /serv/sensor/update
     * <p>
     * Method: POST
     *
     * @param id          int | 广告机ID
     * @param temperature String | 气温
     * @param humidity    String | 湿度
     * @param pm25        String | PM2.5
     * @param pm10        String | PM10
     * @return JSON | 更新结果
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> update(int id, String temperature, String humidity, String pm25, String pm10) {
        MachineSensor sensor = AdvertisementMachineCache.getSensorInfo(id);
        sensor.setTemperature(temperature);
        sensor.setHumidity(humidity);
        sensor.setPm25(pm25);
        sensor.setPm10(pm10);
        HashMap<String, Object> info = Maps.newHashMap();
        if (sensorService.update(sensor) != 0) {
            info.put("responseCode", DONE);
            info.put("message", "天气记录更新");
        } else {
            info.put("responseCode", FAILURE);
            info.put("message", "天气记录更新失败，没有对应的广告机");
        }
        return info;
    }

}
