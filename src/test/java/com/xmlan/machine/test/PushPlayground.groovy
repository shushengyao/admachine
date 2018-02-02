package com.xmlan.machine.test

import cn.jiguang.common.ClientConfig
import cn.jpush.api.JPushClient
import com.google.common.collect.Maps
import com.xmlan.machine.common.config.Global
import com.xmlan.machine.common.util.JsonUtils
import com.xmlan.machine.common.util.PushUtils
import org.junit.Test

/**
 * Created by ayakurayuki on 2017/12/12-10:38.
 * Package: PACKAGE_NAME
 */
class PushPlayground {

    @Test
    void test() {
        def map = Maps.newHashMap()
        map['id'] = 1
        def pushClient = new JPushClient(Global.masterSecret, Global.appKey, null, ClientConfig.instance)
        def payload = PushUtils.buildPayload('ximon', JsonUtils.toJsonString(map), JsonUtils.toJsonString(map))
        def result = pushClient.sendPush(payload)
        println(result)
    }

}
