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

    static PushPayload buildPayload(String alias, String message, HashMap<String, Integer> data) {
        def builder = PushPayload.newBuilder()
        builder.platform = Platform.all()               // 推送平台
        builder.audience = Audience.alias(alias)        // 推送目标
        builder.message = {                             // 推送消息
            def msgBuilder = Message.newBuilder()       // 创建Message.Builder
            msgBuilder.msgContent = message             // 添加message
            // 添加数据到EXTRA_MESSAGE
            if (data != null) {
                def iterator = data.keySet().iterator()
                while (iterator.hasNext()) {
                    String key = iterator.next()
                    msgBuilder.addExtra(key, data.get(key))
                }
            }
            return msgBuilder.build()
        }()
        return builder.build()
    }

}
