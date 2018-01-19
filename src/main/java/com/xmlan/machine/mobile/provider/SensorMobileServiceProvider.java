package com.xmlan.machine.mobile.provider;

import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ayakurayuki on 2018/1/19-14:26. <br/>
 * Package: com.xmlan.machine.mobile.provider <br/>
 */
@Controller
@RequestMapping("${mobilePath}/sensor")
public class SensorMobileServiceProvider extends BaseController {

    /**
     * 通过广告机ID获取天气信息
     *
     * URL: /mob/sensor/info
     *
     * Method: Post
     *
     * @param machineID int 广告机ID
     * @param token     String token身份验证
     * @return 传感器天气信息对象，身份验证不通过则返回null
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public MachineSensor info(int machineID, String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        return AdvertisementMachineCache.getSensorInfo(machineID);
    }

}
