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
        builder.platform = Platform.all()               // 推送平台
        builder.audience = Audience.alias(alias)        // 推送目标
        builder.message = {                             // 推送消息
            def msgBuilder = Message.newBuilder()       // 创建Message.Builder
            msgBuilder.msgContent = message             // 添加message
            if (data.size() != 0) {
                msgBuilder.addExtra('data', data[0])    // 如果有数据，添加到EXTRA_MESSAGE
            }
            return msgBuilder.build()
        }()
        return builder.build()
    }

}
