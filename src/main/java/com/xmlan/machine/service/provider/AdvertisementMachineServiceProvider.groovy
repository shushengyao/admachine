package com.xmlan.machine.service.provider

import com.google.common.collect.Maps
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.util.DateUtils
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by ayakurayuki on 2018/1/8-15:43.
 * Package: com.xmlan.machine.service.provider
 */
@Controller
@RequestMapping('${servicePath}/advertisementMachine')
class AdvertisementMachineServiceProvider extends BaseController {

    @Autowired
    private AdvertisementMachineService service

    @RequestMapping(value = '/get/{codeNumber}', produces = "application/json; charset=utf-8")
    @ResponseBody
    AdvertisementMachine get(@PathVariable String codeNumber) {
        AdvertisementMachineCache.get(codeNumber)
    }

    @RequestMapping(value = '/register', produces = "application/json; charset=utf-8")
    @ResponseBody
    HashMap register(AdvertisementMachine advertisementMachine) {
        if (StringUtils.isBlank(advertisementMachine.addTime)) {
            advertisementMachine.addTime = DateUtils.GetDateTime()
        }
        int result = service.insert advertisementMachine
        def map = Maps.newHashMap()
        map['responseCode'] = "${result}"
        return map
    }

}
