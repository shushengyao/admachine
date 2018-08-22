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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 广告端 广告服务接口
 *
 * Package: com.xmlan.machine.service.provider
 *
 * @author ayakurayuki
 * @date 2018/1/8-09:19
 */
@Controller
@RequestMapping("${servicePath}/advertisement")
public class AdvertisementServiceProvider extends BaseController {

    private final AdvertisementService service;
    private final AdvertisementMachineService machineService;

    @Autowired
    public AdvertisementServiceProvider(AdvertisementService service, AdvertisementMachineService machineService) {
        this.service = service;
        this.machineService = machineService;
    }

    /**
     * 获取广告对象信息
     * <p>
     * URL: /serv/advertisement/get/{id}
     * <p>
     * Method: Get
     *
     * @param id int 广告ID
     * @return Advertisement 广告对象
     */
    @RequestMapping(value = "/get/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Advertisement get(@PathVariable("id") int id) {
        return AdvertisementCache.get(id);
    }

    /**
     * 查询广告列表
     * <p>
     * URL: /serv/advertisement/find
     * <p>
     * Method: POST
     *
     * @param machineID String 广告机ID
     * @return List: Advertisement 广告列表
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<Advertisement> find(String machineID) {
        AdvertisementMachine machine = machineService.get(machineID);
        Advertisement advertisement = new Advertisement();
        advertisement.setMachineID(machine.getId());
        return service.findList(advertisement);
    }

    /**
     * 访问媒体资源
     * <p>
     * URL: /serv/advertisement/media/{id}
     * <p>
     * Method: Get
     *
     * @param id       int 广告ID
     * @param response HttpServletResponse 跳转对象，不需要带入参数
     */
    @RequestMapping("/media/{id}")
    @ResponseBody
    public void media(@PathVariable("id") int id, HttpServletResponse response) {
        Advertisement advertisement = AdvertisementCache.get(id);
        MediaUtils.mediaTransfer(advertisement.getUrl(), response);
        System.out.print(response);
    }

}
