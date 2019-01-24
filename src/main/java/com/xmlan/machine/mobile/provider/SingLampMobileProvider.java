package com.xmlan.machine.mobile.provider;

import com.github.pagehelper.PageInfo;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.singLamp.entity.SingLamp;
import com.xmlan.machine.module.singLamp.service.SingLampService;
import com.xmlan.machine.module.singlelamp_new.entity.SingLampNew;
import com.xmlan.machine.module.singlelamp_new.service.SingLampNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: admachine
 * @description: mobile
 * @author: YSS
 * @create: 2018-11-10 17:13
 **/
@Controller
@RequestMapping("${mobilePath}/singLamp")
public class SingLampMobileProvider {
    @Autowired
    private final SingLampService singLampService;
//    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private final SingLampNewService singLampNewService;

    public SingLampMobileProvider(SingLampService singLampService, ThreadPoolTaskExecutor taskExecutor,SingLampNewService singLampNewService) {
        this.singLampService = singLampService;
        this.singLampNewService = singLampNewService;
//        this.taskExecutor = taskExecutor;
    }

    /**
     * 根据id查询用户名下单灯列表
     * @param userID
     * @param token
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<SingLamp> singLampList(int userID, String token){
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        return singLampService.findListByUserID(userID);
    }




    /**
     * 查询设备的单灯控制器数据
     * @return
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public List<SingLamp> singLampDetail(String token,int machineID){
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        List<SingLamp> list = new ArrayList<>();
        try {
            list = singLampService.findListByMachineID(machineID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据id查询用户名下单灯列表(新的单灯控制器调用)
     * @param userID
     * @param token
     * @return
     */
    @RequestMapping(value = "/newList")
    @ResponseBody
    public List<SingLampNew> singLampNewList(int userID, String token){
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        return singLampNewService.findListByUserID(userID);
    }
    /**
     * 查询设备的单灯控制器数据
     * @return
     */
    @RequestMapping(value = "/newDetail")
    @ResponseBody
    public List<SingLampNew> singLampNewDetail(String token,int machineID){
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        List<SingLampNew> list = new ArrayList<>();
        try {
            list = singLampNewService.findListByMachineID(machineID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
