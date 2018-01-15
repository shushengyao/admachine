package com.xmlan.machine.service.provider

import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.util.MediaUtils
import com.xmlan.machine.module.advertisement.entity.Advertisement
import com.xmlan.machine.module.advertisement.service.AdvertisementService
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletResponse

/**
 * Created by ayakurayuki on 2018/1/8-09:19.
 * Package: com.xmlan.machine.service.provider
 */
@Controller
@RequestMapping('${servicePath}/advertisement')
class AdvertisementServiceProvider extends BaseController {

    @Autowired
    private AdvertisementService service
    @Autowired
    private AdvertisementMachineService machineService

    @RequestMapping(value = '/get/{id}', produces = "application/json; charset=utf-8")
    @ResponseBody
    Advertisement get(@PathVariable int id) {
        AdvertisementCache.get(id)
    }

    @RequestMapping(value = '/find/{codeNumber}', produces = "application/json; charset=utf-8")
    @ResponseBody
    List<Advertisement> findList(@PathVariable String codeNumber) {
        def machine = machineService.getByCodeNumber(codeNumber)
        def advertisement = new Advertisement()
        advertisement.machineID = machine.id
        def list = service.findList advertisement
        return list
    }

    @RequestMapping('/media/{id}')
    @ResponseBody
    void media(@PathVariable String id, HttpServletResponse response) {
        def advertisement = AdvertisementCache.get(id.toInteger())
        MediaUtils.mediaTransfer advertisement.url, response
    }

}
