package com.xmlan.machine.module.advertisementMachine.entity

import com.xmlan.machine.module.user.entity.User

import javax.validation.constraints.NotNull

/**
 * 广告机实体类 <br/>
 * Created by ayakurayuki on 2017/12/12-11:18. <br/>
 * Package: com.xmlan.machine.module.advertisementMachine.entity <br/>
 */
class AdvertisementMachine implements Serializable {

    // 广告机ID
    private int id
    // 用户ID
    private int userID
    // 广告机名称
    private String name
    // 经度
    private String longitude
    // 纬度
    private String latitude
    // 加入时间
    private String addTime
    // 机器标识码(注册码)
    private String codeNumber
    // 灯开关, 1 开灯, 0 关灯
    private int light
    // 充电状态, 1 充电, 0 闲置
    private int charge
    // 选中, 1 选中, 0 未选中
    private int checked
    // 备注
    private String remark
    //摄像头IP
    private String cameraIP
    //摄像头序列号
    private String cameraSequence
    //摄像头设备验证码
    private String cameraVerificationCode
    //摄像头accessToken
    private String accessToken
     //led
    private String LED
    private String city;
    private String cityCode;//城市编码
    private String updateTime;//定位更新时间
    private String addrStr;//定位地址

    private int singLampID;String getCityCode() {
        return cityCode
    }

    void setCityCode(String cityCode) {
        this.cityCode = cityCode
    }

    String getUpdateTime() {
        return updateTime
    }

    void setUpdateTime(String updateTime) {
        this.updateTime = updateTime
    }

    String getAddrStr() {
        return addrStr
    }

    void setAddrStr(String addrStr) {
        this.addrStr = addrStr
    }

    String getCity() {
        return city
    }

    void setCity(String city) {
        this.city = city
    }

    int getSingLampID() {
        return singLampID
    }

    void setSingLampID(int singLampID) {
        this.singLampID = singLampID
    }

    String getLED() {
        return LED
    }

    void setLED(String LED) {
        this.LED = LED
    }
//用户列表
    private List<User> userList

    List<User> getUserList() {
        return userList
    }

    void setUserList(List<User> userList) {
        this.userList = userList
    }

    String getAccessToken() {
        return accessToken
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken
    }

    String getCameraSequence() {
        return cameraSequence
    }

    void setCameraSequence(String cameraSequence) {
        this.cameraSequence = cameraSequence
    }

    String getCameraVerificationCode() {
        return cameraVerificationCode
    }

    void setCameraVerificationCode(String cameraVerificationCode) {
        this.cameraVerificationCode = cameraVerificationCode
    }

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

    int getLight() {
        return light
    }

    void setLight(int light) {
        this.light = light
    }

    int getCharge() {
        return charge
    }

    void setCharge(int charge) {
        this.charge = charge
    }

    int getChecked() {
        return checked
    }

    void setChecked(int checked) {
        this.checked = checked
    }

    String getRemark() {
        return remark
    }

    void setRemark(String remark) {
        this.remark = remark
    }

    String getCameraIP() {
        return cameraIP
    }

    void setCameraIP(String cameraIP) {
        this.cameraIP = cameraIP
    }
}
