package com.xmlan.machine.common.base

/**
 * 操作类型枚举
 *
 * com.xmlan.machine.common.base
 *
 * @author ayakurayuki
 * @date 2018/3/13-15:22
 */
enum OperateEnum {

    New("新建"), Update("修改"), Delete("删除"), Push("推送"),
    Register("注册"), Login("登录"), Logout("正常退出")

    private final String description

    private OperateEnum(String description) {
        this.description = description
    }

    final String getKey() {
        this.toString()
    }

    final String getDescription() {
        return description
    }

    final static String getDescription(String originKey) {
        for (value in values()) {
            if (value.toString() == originKey) {
                return value.description
            }
        }
        return ""
    }

}