package com.xmlan.machine.common.timer;

import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.monitor.MonitorController;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * 定时器执行内容类
 * @program: admachine
 * @description: timer
 * @author: YSS
 * @create: 2018-06-19 15:58
 **/
@Component
public class NFDFlightDataTimerTask extends TimerTask {
    @Autowired
    AdvertisementMachineService service;

//    public AdvertisementMachineService getService() {
//        return service;
//    }
//    public void setService(AdvertisementMachineService service) {
//        this.service = service;
//    }

//    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void run(){
        try {
            accessToken("123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据库accessToken字段值
     * @param accessToken
     */
    public void accessToken (String accessToken){
        service.updateAccessToken(accessToken);
    }
}
