package com.xmlan.machine.module.baiduMap.web;


import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.cache.MachineGroupCache;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.machineGroup.entity.MachineGroup;
import com.xmlan.machine.module.machineGroup.service.MachineGroupService;
import com.xmlan.machine.module.user.entity.User;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    @Autowired
    private MachineGroupService groupService;

    /**
     *  打开初始化地图
     * @return
     */
    @RequestMapping(value = "/map/{pageNo}")
    String map(Model model,ModelMap modelMap,@PathVariable int pageNo){
        Object object = modelMap.get("loginUser");
        User user = (User) object;
        int userID = user.getId();
        if (userID==1 || userID == 10){
            List<MachineGroup> groupList = groupService.findAll(pageNo);
            PageInfo<MachineGroup> page = new PageInfo<>(groupList);
            model.addAttribute ("page", page);
            model.addAttribute("machineGroupList",groupList);
        }else if (user.getRoleID() ==1){
            List<MachineGroup> groupList = groupService.findAllByUserID(userID,pageNo);
            PageInfo<MachineGroup> page = new PageInfo<>(groupList);
            model.addAttribute ("page", page);
            model.addAttribute("machineGroupList",groupList);
        }

        return "baiduMap/baiduMap";
    }

    /**
     * 显示所有设备
     * @return
     */
    @RequestMapping(value = "/index", produces = "application/json; charset=utf-8")
    @ResponseBody
    List<AdvertisementMachine> index(AdvertisementMachine advertisementMachine,ModelMap modelMap){
        List<AdvertisementMachine> list;
        Object object = modelMap.get("loginUser");
        User user = (User) object;
        int userID = user.getId();
        advertisementMachine.setUserID(userID);
        if (userID==1 || userID == 10){
            list= service.findAllMachine();
            return list;
        }else if (user.getRoleID() ==1){
            list =service.atmosphereListByUserID(userID);
        }else {
            list =service.generalFindList(advertisementMachine);
        }
        return list;
    }

}
