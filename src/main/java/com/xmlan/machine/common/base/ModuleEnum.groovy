package com.xmlan.machine.common.base

/**
 * 日志内容类型枚举
 * <p>
 * com.xmlan.machine.common.base
 *
 * @author ayakurayuki
 * @date 2018/3/13-14:39
 */
final enum ModuleEnum {

    Advertisement("广告"), Machine("广告机"), User("用户"), Role("角色")

    private final String description

    private ModuleEnum(String description) {
        this.description = description
    }

    final String getDescription() {
        return description
    }

    final String getDescription(String originKey) {
        for (value in values()) {
            if (value.toString() == originKey) {
                return value.description
            }
        }
        return ""
    }

}
