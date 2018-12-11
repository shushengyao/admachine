package com.xmlan.machine.mobile.provider;

import com.github.pagehelper.PageInfo;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.singLamp.entity.SingLamp;
import com.xmlan.machine.module.singLamp.service.SingLampService;
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
    private final ThreadPoolTaskExecutor taskExecutor;

    public SingLampMobileProvider(SingLampService singLampService, ThreadPoolTaskExecutor taskExecutor) {
        this.singLampService = singLampService;
        this.taskExecutor = taskExecutor;
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
    public List<SingLamp> singLampDetail(String token,int id){
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        List<SingLamp> list = new ArrayList<>();
        try {
            list = singLampService.findListByMachineID(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
