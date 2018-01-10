package com.xmlan.machine.mobile.provider

import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by ayakurayuki on 2018/1/9-14:44.
 * Package: com.xmlan.machine.mobile.provider
 */
@Controller
@RequestMapping('${mobilePath}/advertisementMachine')
class AdvertisementMachineMobileServiceProvider extends BaseController {

    @Autowired
    private AdvertisementMachineService service

    private static boolean isNotBlank(String... args) {
        for (string in args) {
            if (StringUtils.isBlank(string)) {
                return false
            }
        }
        return true
    }

    @RequestMapping(value = '/get/{id}', produces = "application/json; charset=utf-8")
    @ResponseBody
    AdvertisementMachine get(@PathVariable String id) {
        return service.get(id)
    }

    @RequestMapping(value = '/find/{userID}', produces = "application/json; charset=utf-8")
    @ResponseBody
    List<AdvertisementMachine> find(@PathVariable int userID) {
        AdvertisementMachine machine = new AdvertisementMachine()
        machine.userID = userID
        return service.findForProvider(machine)
    }

    @RequestMapping(value = '/position/query', produces = "application/json; charset=utf-8")
    @ResponseBody
    List<AdvertisementMachine> positionQuery(String minLongitude, String maxLongitude, String minLatitude, String maxLatitude) {
        if (isNotBlank(minLongitude, maxLongitude, minLatitude, maxLatitude)) {
            return service.positionQuery(minLongitude, maxLongitude, minLatitude, maxLatitude)
        }
        return null
    }

}
