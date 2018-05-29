package com.xmlan.machine.module.baiduMap.web;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-05-29 13:37
 * 地图显示
 **/
@Controller
@RequestMapping("${adminPath}/baiduMap")
public class MapController extends BaseController {
    @Autowired
    private AdvertisementMachineService service;

    /**
     *  打开初始化地图
     * @return
     */
    @RequestMapping(value = "/map")
    String map(){
        return "baiduMap/baiduMap";
    }

    /**
     * 显示所有设备
     * @return
     */
    @RequestMapping(value = "/index", produces = "application/json; charset=utf-8")
    @ResponseBody
    Map index(AdvertisementMachine advertisementMachine){
        List<AdvertisementMachine> list = service.findAll();
        Map<Integer, Object> machine = Maps.newHashMap();
        for (int i = 0;i<list.size();i++){
            list.get(i).getUserID();
            if (SessionUtils.getAdmin(request).getId() ==list.get(i).getUserID() ||SessionUtils.getAdmin(request).getRoleID() ==ADMIN_ROLE_ID ) {
                advertisementMachine.setUserID(SessionUtils.getAdmin(request).getId());
                machine.put(i,list.get(i));
            }
        }
        return machine;
    }
}
