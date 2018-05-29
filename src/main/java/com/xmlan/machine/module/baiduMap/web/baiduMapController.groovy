package com.xmlan.machine.module.baiduMap.web;

import com.google.common.collect.Maps;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-05-07 14:34
 * 百度地图api后台
 **/
@Controller
@RequestMapping('${adminPath}/baiduMaps')
public class baiduMapController {
    @Autowired
    private AdvertisementMachineService service;
    /**
     *  打开初始化地图
     * @return
     */
    @RequestMapping(value = "/maps")
     String map(){
        return "baiduMap/baiduMap";
    }
    /**
     * 显示所有设备
     * @return
     */
    @RequestMapping(value = "/indexs", produces = "application/json; charset=utf-8")
    @ResponseBody
     Map<String, Object> index(){
        List<AdvertisementMachine> list = service.findAll();
        Map<String, Object> data = Maps.newHashMap();
        def machine = list
        data["machine"] = machine
        return data
    }
}
