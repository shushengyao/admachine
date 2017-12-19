package com.xmlan.machine.module.advertisementMachine.entity;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ayakurayuki on 2017/12/12-11:18.
 * Package: com.xmlan.machine.module.advertisementMachine.entity
 */
@Alias("AdvertisementMachine")
public class AdvertisementMachine {

    private int id; // 广告机ID
    private int userID; // 用户ID
    private String name; // 广告机名称
    private String address; // 地址
    private double longitude; // 经度
    private double latitude; // 纬度
    private Date addTime; // 加入时间
    private String codeNumber; // 机器标识码(注册码)
    private String remark; // 备注

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull(message = "所属的用户ID不能为空")
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @NotNull(message = "广告机名称不能为空")
    public String getName() {
        return name;
    }

    @Length(min = 1, max = 64)
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
