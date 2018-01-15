package com.xmlan.machine.mobile.provider

import cn.jiguang.common.ClientConfig
import cn.jiguang.common.resp.APIRequestException
import cn.jpush.api.JPushClient
import com.google.common.collect.Maps
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.config.Global
import com.xmlan.machine.common.util.JsonUtils
import com.xmlan.machine.common.util.PushUtils
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
        def machine = new AdvertisementMachine()
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

    @RequestMapping(value = '/light', produces = "application/json; charset=utf-8")
    @ResponseBody
    Map light(int id, int operate) {
        def responseCode = service.lightControl(id, operate)
        def map = Maps.newHashMap()
        map['responseCode'] = responseCode
        if (responseCode == NO_SUCH_ROW) {
            map['message'] = "目标广告机不存在"
        } else if (responseCode == ERROR_REQUEST) {
            map['message'] = "操作码不正确"
        } else if (responseCode == DONE) {
            map['message'] = operate == 1 ? "开灯！" : "关灯！"
            def command = Maps.newHashMap()
            command['id'] = id
            command['operate'] = operate
            def pushClient = new JPushClient(Global.masterSecret, Global.appKey, null, ClientConfig.instance)
            def payload = PushUtils.buildPayload("${id}", "Light switch.", JsonUtils.toJsonString(command))
            try {
                def result = pushClient.sendPush(payload)
                logger.trace(result)
            } catch (APIRequestException e) {
                logger.error "API exception with: ${e.message}"
            }
        } else {
            map['message'] = "系统繁忙"
        }
        return map
    }

}
