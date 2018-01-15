package com.xmlan.machine.service.provider

import cn.jiguang.common.ClientConfig
import cn.jiguang.common.resp.APIRequestException
import cn.jpush.api.JPushClient
import com.google.common.collect.Maps
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.config.Global
import com.xmlan.machine.common.util.DateUtils
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
        map['responseCode'] = result
        map['id'] = "${service.getByCodeNumber(advertisementMachine.codeNumber).id}"
        return map
    }

    @RequestMapping(value = '/charge', produces = "application/json; charset=utf-8")
    @ResponseBody
    Map charge(int id, int operate) {
        def responseCode = service.chargeControl(id, operate)
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
