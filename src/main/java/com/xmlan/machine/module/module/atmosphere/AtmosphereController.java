package com.xmlan.machine.module.module.atmosphere;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.advertisementMachine.service.MachineSensorService;
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
     * 进入大气主页
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(){
        return "atmosphere/index";
    }
    /**
     * 打开大气数据采集
     * @return
     */
    @RequestMapping(value = "/index")
    @ResponseBody
    public Map index(AdvertisementMachine advertisementMachine){
        List<AdvertisementMachine> listad =adservice.findAll();
        Map<Integer, Object> machine = Maps.newHashMap();
        for (int i=0;i<listad.size();i++){
            listad.get(i).getUserID();
            if (SessionUtils.getAdmin(request).getId() ==listad.get(i).getUserID() ||SessionUtils.getAdmin(request).getRoleID() ==ADMIN_ROLE_ID ) {
                advertisementMachine.setUserID(SessionUtils.getAdmin(request).getId());
                machine.put(i,listad.get(i));
            }
        }
        return machine;
    }
}
