package com.xmlan.machine.service.provider;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.base.ModuleEnum;
import com.xmlan.machine.common.base.ObjectEnum;
import com.xmlan.machine.common.base.OperateEnum;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.util.DateUtils;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.system.service.SysLogService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 广告端 广告机服务接口
 * <p>
 * Package: com.xmlan.machine.service.provider
 *
 * @author ayakurayuki
 * @date 2018/1/8-15:43
 */
@Controller
@RequestMapping("${servicePath}/advertisementMachine")
public class AdvertisementMachineServiceProvider extends BaseController {

    private final AdvertisementMachineService service;
    private final SysLogService sysLogService;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public AdvertisementMachineServiceProvider(
            AdvertisementMachineService service,
            SysLogService sysLogService,
            ThreadPoolTaskExecutor taskExecutor) {
        this.service = service;
        this.sysLogService = sysLogService;
        this.taskExecutor = taskExecutor;
    }

    /**
     * 获取广告机对象信息
     * <p>
     * URL: /serv/advertisementMachine/get/{id}
     * <p>
     * Method: Get
     *
     * @param id int 广告机ID
     * @return AdvertisementMachine 广告机对象
     */
    @RequestMapping(value = "/get/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public AdvertisementMachine get(@PathVariable("id") int id) {
        return AdvertisementMachineCache.get(id);
    }

    /**
     * 注册广告机
     * <p>
     * URL: /serv/advertisementMachine/register
     * <p>
     * Method: POST
     *
     * @param advertisementMachine AdvertisementMachine 广告机对象
     * @return 注册后的结果
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> register(AdvertisementMachine advertisementMachine) {
        AdvertisementMachine temp = AdvertisementMachineCache.get(advertisementMachine.getCodeNumber());
        if (temp != null) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put(keyResponseCode, DATABASE_DO_NOTHING);
            map.put(keyErrorMessage, "Exists machine with the same code number.");
            map.put(keyID, temp.getId());
            return map;
        }
        if (StringUtils.isBlank(advertisementMachine.getAddTime())) {
            advertisementMachine.setAddTime(DateUtils.getDateTime());
        }
        advertisementMachine.setName(StringUtils.EMPTY);
        advertisementMachine.setUserID(-2);
        advertisementMachine.setAddress(StringUtils.EMPTY);
        advertisementMachine.setLongitude("0.0");
        advertisementMachine.setLatitude("0.0");
        advertisementMachine.setLight(0);
        advertisementMachine.setCharge(0);
        advertisementMachine.setChecked(0);
        int result = service.insert(advertisementMachine);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put(keyResponseCode, result);
        map.put(keyID, service.getByCodeNumber(advertisementMachine.getCodeNumber()).getId());
        taskExecutor.execute(() -> sysLogService.log(
                ModuleEnum.Machine,
                OperateEnum.Register,
                service.getByCodeNumber(advertisementMachine.getCodeNumber()).getId(),
                ObjectEnum.Machine,
                "新注册了一台广告机"
        ));
        return map;
    }

    /**
     * 更新广告机的经纬坐标
     * <p>
     * URL: /serv/advertisementMachine/gps/update/{id}
     * <p>
     * Method: Get/Post
     *
     * @param id        int 广告机ID
     * @param longitude String 经度
     * @param latitude  String 纬度
     * @return 更新结果
     */
    @RequestMapping(value = "/gps/update/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> gpsUpdate(@PathVariable("id") int id, String longitude, String latitude) {
        HashMap<String, Object> map = Maps.newHashMap();
        if (NumberUtils.isNumber(longitude) && NumberUtils.isNumber(latitude)) {
            int responseCode = service.updateLocation(id, longitude, latitude);
            if (responseCode == DONE) {
                map.put(keyResponseCode, DONE);
                map.put(keyMessage, "Updated!");
            } else {
                map.put(keyResponseCode, responseCode);
                map.put(keyMessage, "Update failed!");
            }
        } else {
            map.put(keyResponseCode, DATABASE_DO_NOTHING);
            map.put(keyMessage, "Longitude or Latitude is not a number.");
        }
        return map;
    }

}
