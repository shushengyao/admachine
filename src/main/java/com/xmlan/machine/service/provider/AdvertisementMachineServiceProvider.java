package com.xmlan.machine.service.provider;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.util.DateUtils;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by ayakurayuki on 2018/1/8-15:43.
 * Package: com.xmlan.machine.service.provider
 */
@Controller
@RequestMapping("${servicePath}/advertisementMachine")
public class AdvertisementMachineServiceProvider extends BaseController {

    @Autowired
    private AdvertisementMachineService service;

    /**
     * 获取广告机对象信息
     * <p>
     * URL: /serv/advertisementMachine/get/{id}
     * <p>
     * Method: Get
     *
     * @param id int | 广告机ID
     * @return AdvertisementMachine | 广告机对象
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
     * @param advertisementMachine AdvertisementMachine | 广告机对象
     * @return 注册后的结果
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> register(AdvertisementMachine advertisementMachine) {
        AdvertisementMachine temp = AdvertisementMachineCache.get(advertisementMachine.getCodeNumber());
        if (temp != null) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("responseCode", DATABASE_DO_NOTHING);
            map.put("errorMessage", "Exists machine with the same code number.");
            return map;
        }
        if (StringUtils.isBlank(advertisementMachine.getAddTime())) {
            advertisementMachine.setAddTime(DateUtils.GetDateTime());
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
        map.put("responseCode", result);
        map.put("id", service.getByCodeNumber(advertisementMachine.getCodeNumber()).getId());
        return map;
    }

    /**
     * 更新广告机的经纬坐标
     * <p>
     * URL: /serv/advertisementMachine/gps/update/{id}
     * <p>
     * Method: Get
     *
     * @param id        int | 广告机ID
     * @param longitude String | 经度
     * @param latitude  String | 纬度
     * @return 更新结果
     */
    @RequestMapping(value = "/gps/update/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap<String, Object> gpsUpdate(@PathVariable("id") int id, String longitude, String latitude) {
        HashMap<String, Object> map = Maps.newHashMap();
        AdvertisementMachine machine = AdvertisementMachineCache.get(id);
        if (machine == null) {
            map.put("responseCode", NO_SUCH_ROW);
            map.put("message", "No ad machine selected.");
            return map;
        }
        if (NumberUtils.isNumber(longitude) && NumberUtils.isNumber(latitude)) {
            machine.setLongitude(longitude);
            machine.setLatitude(latitude);
            if (service.update(machine) != 0) {
                map.put("responseCode", DONE);
                map.put("message", "Updated!");
            } else {
                map.put("responseCode", FAILURE);
                map.put("message", "Update failed!");
            }
        } else {
            map.put("responseCode", DATABASE_DO_NOTHING);
            map.put("message", "Longitude or Latitude is not a number.");
        }
        return map;
    }

}
