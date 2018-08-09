package com.xmlan.machine.module.atmosphere;

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
    protected MachineSensorService service;

    @Autowired
    private AdvertisementMachineService adservice;

    /**
     * 进入主页
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(){
        return "atmosphere/index";
    }
    /**
     * 数据采集
     * @return
     */
    @RequestMapping(value = "/index")
    @ResponseBody
    public List<AdvertisementMachine> index(ModelMap modelMap, @RequestParam("pageNo") int pageNo){
        if ("".equals(pageNo)){
            pageNo=1;
        }
        User user=(User)modelMap.get("loginUser");
        int userid = user.getId();
        List<AdvertisementMachine> machineList;
        if (userid==1 || userid == 10){
            machineList =adservice.findAllMachine();
            return machineList;
        }else {
            machineList =adservice.adchineListByUserID(userid,pageNo);
            return machineList;
        }
    }
}
