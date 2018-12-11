package com.xmlan.machine.module.machineGroup.entity;

import java.io.Serializable;

/**
 * 分组列表
 * @program: admachine
 * @description: entity
 * @author: YSS
 * @create: 2018-11-05 17:23
 **/
public class SimpleMachineGroup implements Serializable {
    int id;
    String groupName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
