package com.xmlan.machine.common.base

/**
 * 操作对象枚举
 *
 * com.xmlan.machine.common.base
 *
 * @author ayakurayuki
 * @date 2018/3/13-16:05
 */
enum ObjectEnum {

    User("用户"), Machine("广告机")

    private final String description

    private ObjectEnum(String description) {
        this.description = description
    }

    final String getDescription() {
        return description
    }

    final String getKey() {
        this.toString()
    }

    final static String getDescription(String originKey) {
        for (value in values()) {
            if (value.toString() == originKey) {
                return value.description
            }
        }
        return ""
    }

    final static ObjectEnum getObjectType(String description) {
        for (value in values()) {
            if (value.description == description) {
                return value
            }
        }
        return null
    }

}