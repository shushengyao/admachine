package com.xmlan.machine.service.provider

import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.util.MediaUtils
import com.xmlan.machine.module.advertisement.entity.Advertisement
import com.xmlan.machine.module.advertisement.service.AdvertisementService
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
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

    @RequestMapping('/get/{id}')
    @ResponseBody
    Advertisement get(@PathVariable String id) {
        service.get id
    }

    @RequestMapping('/find/{codeNumber}')
    @ResponseBody
    List<Advertisement> findList(@PathVariable String codeNumber) {
        AdvertisementMachine machine = machineService.getByCodeNumber(codeNumber)
        Advertisement advertisement = new Advertisement()
        advertisement.machineID = machine.id
        List<Advertisement> list = service.findList advertisement
        return list
    }

    @RequestMapping('/media/{id}')
    @ResponseBody
    void media(@PathVariable String id, HttpServletResponse response) {
        Advertisement advertisement = AdvertisementCache.get(id.toInteger())
        MediaUtils.mediaTransfer advertisement.url, response
    }

}
