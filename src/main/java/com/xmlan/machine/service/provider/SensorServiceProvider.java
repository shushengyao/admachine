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
 * 广告端 天气传感器数据服务接口
 * <p>
 * Package: com.xmlan.machine.service.provider
 *
 * @author ayakurayuki
 * @date 2018/1/19-14:14
 */
@Controller
@RequestMapping("${servicePath}/sensor")
public class SensorServiceProvider extends BaseController {

    private final MachineSensorService sensorService;

    /**
     * 构造器注入方式
     *
     * @param sensorService 传感器Service层对象
     */
    @Autowired
    public SensorServiceProvider(MachineSensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * 获取广告机传感器天气数据
     * <p>
     * URL: /serv/sensor/status
     * <p>
     * Method: POST
     *
     * @param id int 广告机ID
     * @return MachineSensor 天气数据对象
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
     * @param id          int 广告机ID
     * @param temperature String 气温
     * @param humidity    String 湿度
     * @param pm25        String PM2.5
     * @param brightness  String 亮度
     * @return JSON 更新结果
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> update(int id, String temperature, String humidity, String pm25, String brightness) {
        MachineSensor sensor = AdvertisementMachineCache.getSensorInfo(id);
        sensor.setTemperature(temperature);
        sensor.setHumidity(humidity);
        sensor.setPm25(pm25);
        sensor.setBrightness(brightness);
        HashMap<String, Object> info = Maps.newHashMap();
        if (sensorService.update(sensor) != 0) {
            info.put(keyResponseCode, DONE);
            info.put(keyMessage, "天气记录更新");
        } else {
            info.put(keyResponseCode, FAILURE);
            info.put(keyMessage, "天气记录更新失败，没有对应的广告机");
        }
        return info;
    }

}
