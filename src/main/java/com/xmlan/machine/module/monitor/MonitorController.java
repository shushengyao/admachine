package com.xmlan.machine.module.monitor;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-05-11 14:54
 * 监控控制类
 **/
@Controller
@RequestMapping("${adminPath}/monitor")
public class MonitorController extends BaseController {
    @Autowired
    private AdvertisementMachineService service;

    /**
     * 进入监控主页
     * @return
     */
    @RequestMapping(value = "/Monitorlist")
    public String list(){
        return "monitor/Monitorlist";
    }

    /**
     * 打开所有监控
     * @return
     */
    @RequestMapping(value = "/index")
    @ResponseBody
    public Map index(AdvertisementMachine advertisementMachine){
        List<AdvertisementMachine> list = service.findAll();
        Map<Integer, Object> machine = Maps.newHashMap();
        for (int i=0;i<list.size();i++){
            list.get(i).getUserID();
            if (SessionUtils.getAdmin(request).getId() ==list.get(i).getUserID() ||SessionUtils.getAdmin(request).getRoleID() ==ADMIN_ROLE_ID ) {
                advertisementMachine.setUserID(SessionUtils.getAdmin(request).getId());
                machine.put(i,list.get(i));
            }
        }
        return machine;
    }
}
