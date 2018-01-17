package com.xmlan.machine.service.provider;

import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementCache;
import com.xmlan.machine.common.util.MediaUtils;
import com.xmlan.machine.module.advertisement.entity.Advertisement;
import com.xmlan.machine.module.advertisement.service.AdvertisementService;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ayakurayuki on 2018/1/8-09:19.
 * Package: com.xmlan.machine.service.provider
 */
@Controller
@RequestMapping("${servicePath}/advertisement")
public class AdvertisementServiceProvider extends BaseController {

    @Autowired
    private AdvertisementService service;
    @Autowired
    private AdvertisementMachineService machineService;

    @RequestMapping(value = "/get/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Advertisement get(@PathVariable int id) {
        return AdvertisementCache.get(id);
    }

    @PostMapping(value = "/find", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<Advertisement> findList() {
        String id = request.getParameter("id");
        AdvertisementMachine machine = machineService.get(id);
        Advertisement advertisement = new Advertisement();
        advertisement.setMachineID(machine.getId());
        return service.findList(advertisement);
    }

    @RequestMapping("/media/{id}")
    @ResponseBody
    public void media(@PathVariable int id, HttpServletResponse response) {
        Advertisement advertisement = AdvertisementCache.get(id);
        MediaUtils.mediaTransfer(advertisement.getUrl(), response);
    }

}
