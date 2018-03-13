package com.xmlan.machine.common.base

/**
 * Created by ayakurayuki on 2018/1/12-18:24. <br/>
 * Package: com.xmlan.machine.common.base <br/>
 * 加密类型枚举类
 * <p>
 * 支持类型
 * <pre>MD5</pre>
 * <pre>SHA-1</pre>
 * <pre>SHA256</pre>
 */
final enum AlgorithmEnum {

    MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256")

    /**
     * 类型所使用的值，调用枚举时使用
     */
    private final String type

    private AlgorithmEnum(String type) {
        this.type = type
    }

    final String getType() {
        return type
    }

    final String getType(String originKey) {
        for (value in values()) {
            if (value.toString() == originKey) {
                return value.type
            }
        }
        return ""
    }

}