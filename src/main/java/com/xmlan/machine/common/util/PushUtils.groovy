package com.xmlan.machine.common.util

import cn.jpush.api.push.model.Message
import cn.jpush.api.push.model.Platform
import cn.jpush.api.push.model.PushPayload
import cn.jpush.api.push.model.audience.Audience

/**
 * Created by ayakurayuki on 2018/1/12-16:16.
 * Package: com.xmlan.machine.common.util
 */
class PushUtils {

    static PushPayload buildPayload(String alias, String message, String... data) {
        def builder = PushPayload.newBuilder()
        builder.platform = Platform.all()
        builder.audience = Audience.alias(alias)
        builder.message = {
            def msgBuilder = Message.newBuilder()
            msgBuilder.msgContent = message
            if (data.size() != 0) {
                msgBuilder.addExtra('data', data[0])
            }
            return msgBuilder.build()
        }()
        return builder.build()
    }

}
