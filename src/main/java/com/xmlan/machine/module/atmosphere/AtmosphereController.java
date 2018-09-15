package com.xmlan.machine.module.atmosphere;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.advertisementMachine.service.MachineSensorService;
import com.xmlan.machine.module.user.entity.User;
import com.xmlan.machine.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-05-25 16:53
 * 大气采集类
 **/
@Controller
@RequestMapping("${adminPath}/atmosphere")
public class AtmosphereController extends BaseController {
    @Autowired
    private AdvertisementMachineService adservice;

    /**
     * 数据采集主页
     * @return
     */
    @RequestMapping(value = "/list/{pageNo}")
    public  String list(AdvertisementMachine advertisementMachine,ModelMap modelMap, @PathVariable int pageNo,Model model){
        if ("".equals(pageNo)){
            pageNo=1;
        }
        User user=(User)modelMap.get("loginUser");
        int userID = user.getId();
        advertisementMachine.setUserID(userID);
        List<AdvertisementMachine> machineList;
        if (userID==1 || userID == 10){
            machineList =adservice.findAll(pageNo);
        }else if (user.getRoleID() ==1){
            machineList =adservice.atmosphereListByUserID(userID,pageNo);
        }else {
            machineList =adservice.generalFindList(advertisementMachine,pageNo);
        }
        PageInfo<AdvertisementMachine> page = new PageInfo<>(machineList);
        model.addAttribute( "page", page);
        return "atmosphere/index";
    }
}
