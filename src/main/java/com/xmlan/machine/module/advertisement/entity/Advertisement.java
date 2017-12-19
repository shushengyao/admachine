package com.xmlan.machine.module.advertisement.entity;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ayakurayuki on 2017/12/13-08:55.
 * Package: com.xmlan.machine.module.advertisement.entity
 */
@Alias("Advertisement")
public class Advertisement {

    private int id; // 广告ID
    private String name; // 广告名称
    private String url; // 图片或视频地址
    private int time; // 播放时间
    private Date addTime; // 加入时间
    private int machineID; // 机器识别码
    private String remark; // 备注

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull(message = "广告名称不能为空")
    public String getName() {
        return name;
    }

    @Length(min = 1, max = 96)
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @NotNull(message = "不能没有所属广告机的ID")
    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
