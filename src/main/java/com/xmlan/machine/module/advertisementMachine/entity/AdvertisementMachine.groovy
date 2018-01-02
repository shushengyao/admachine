package com.xmlan.machine.module.advertisementMachine.entity

import org.apache.ibatis.type.Alias

import javax.validation.constraints.NotNull

/**
 * Created by ayakurayuki on 2017/12/12-11:18.
 * Package: com.xmlan.machine.module.advertisementMachine.entity
 */
@Alias("AdvertisementMachine")
class AdvertisementMachine implements Serializable {

    private int id // 广告机ID
    private int userID // 用户ID
    private String name // 广告机名称
    private String address // 地址
    private String longitude // 经度
    private String latitude // 纬度
    private String addTime // 加入时间
    private String codeNumber // 机器标识码(注册码)
    private String remark // 备注

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    @NotNull(message = "不能为空")
    int getUserID() {
        return userID
    }

    void setUserID(int userID) {
        this.userID = userID
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getAddress() {
        return address
    }

    void setAddress(String address) {
        this.address = address
    }

    String getLongitude() {
        return longitude
    }

    void setLongitude(String longitude) {
        this.longitude = longitude
    }

    String getLatitude() {
        return latitude
    }

    void setLatitude(String latitude) {
        this.latitude = latitude
    }

    String getAddTime() {
        return addTime
    }

    void setAddTime(String addTime) {
        this.addTime = addTime
    }

    String getCodeNumber() {
        return codeNumber
    }

    void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber
    }

    String getRemark() {
        return remark
    }

    void setRemark(String remark) {
        this.remark = remark
    }

}
