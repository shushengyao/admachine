package com.xmlan.machine.module.advertisement.entity

import org.hibernate.validator.constraints.Length

import javax.validation.constraints.NotNull

/**
 * Created by ayakurayuki on 2017/12/13-08:55.
 * <p>
 * Package: com.xmlan.machine.module.advertisement.entity
 * <p>
 * 广告实体类
 */
class Advertisement implements Serializable {

    // 广告ID
    private int id
    // 广告名称
    private String name
    // 图片或视频地址
    private String url
    // 播放时间
    private int time
    // 加入时间
    private String addTime
    // 机器识别码
    private int machineID
    // 用户ID
    private int userID
    // 备注
    private String remark

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    @NotNull(message = "广告名称不能为空")
    @Length(min = 1, max = 96)
    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    int getTime() {
        return time
    }

    void setTime(int time) {
        this.time = time
    }

    String getAddTime() {
        return addTime
    }

    void setAddTime(String addTime) {
        this.addTime = addTime
    }

    @NotNull(message = "不能没有所属广告机的ID")
    int getMachineID() {
        return machineID
    }

    void setMachineID(int machineID) {
        this.machineID = machineID
    }

    int getUserID() {
        return userID
    }

    void setUserID(int userID) {
        this.userID = userID
    }

    String getRemark() {
        return remark
    }

    void setRemark(String remark) {
        this.remark = remark
    }

}
