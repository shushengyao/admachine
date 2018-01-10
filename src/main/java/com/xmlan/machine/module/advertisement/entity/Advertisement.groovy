package com.xmlan.machine.module.advertisement.entity

import org.apache.ibatis.type.Alias
import org.hibernate.validator.constraints.Length

import javax.validation.constraints.NotNull

/**
 * Created by ayakurayuki on 2017/12/13-08:55.
 * Package: com.xmlan.machine.module.advertisement.entity
 */
@Alias("Advertisement")
class Advertisement implements Serializable {

    private int id
    // 广告ID
    private String name
    // 广告名称
    private String url
    // 图片或视频地址
    private int time
    // 播放时间
    private String addTime
    // 加入时间
    private int machineID
    // 机器识别码
    private int userID
    // 用户ID
    private String remark
    // 备注

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
