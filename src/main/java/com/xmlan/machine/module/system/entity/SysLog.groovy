package com.xmlan.machine.module.system.entity

import com.xmlan.machine.common.util.JsonUtils

/**
 * 系统日志记录类
 * <p>
 * com.xmlan.machine.module.system.entity
 *
 * @author ayakurayuki
 * @date 2018/3/13-16:10
 */
class SysLog implements Serializable {

    // ID
    private int id
    // 类别/模块 - ModuleEnum
    private String type
    // 操作类型 - OperateEnum
    private String operate
    // 操作者ID
    private int operator
    // 操作者对象类别 - ObjectEnum
    private String operatorObject
    // 描述
    private String description
    // 记录时间
    private String logDate

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getOperate() {
        return operate
    }

    void setOperate(String operate) {
        this.operate = operate
    }

    int getOperator() {
        return operator
    }

    void setOperator(int operator) {
        this.operator = operator
    }

    String getOperatorObject() {
        return operatorObject
    }

    void setOperatorObject(String operatorObject) {
        this.operatorObject = operatorObject
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getLogDate() {
        return logDate
    }

    void setLogDate(String logDate) {
        this.logDate = logDate
    }


    @Override
    String toString() {
        return JsonUtils.toJsonString(this)
    }

}
