package com.xmlan.machine.module.machineGroup.entity;

import java.io.Serializable;

/**
 * @program: admachine
 * @description: entity
 * @author: YSS
 * @create: 2018-09-28 10:54
 **/
public class MachineGroup implements Serializable {
    //id
    private int id;
    //用户id
    private int userID;
    //设备id
    private String machineID;
    // 灯开关, 1 开灯, 0 关灯
    private int light;
    //组名
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }
}
