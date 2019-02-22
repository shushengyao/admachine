package com.xmlan.machine.module.user.entity

import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import org.hibernate.validator.constraints.Length

import javax.validation.constraints.NotNull

/**
 * Created by ayakurayuki on 2017/12/12-10:46.
 * Package: com.xmlan.machine.module.user.entity
 */
class User implements Serializable {

    private int id
    // 用户ID
    private String username
    // 用户名
    private String authname
    // 登录账号
    private String password
    // 密码, 建议SHA-256 x 10
    private int roleID
    // 角色ID
    private String address
    //用户头像地址
    private String url
    // 地址
    private String addTime
    // 加入时间
    private String phone
    // 联系电话
    private String remark
    // 备注
    private String founder
    //创建人

    //设备列表
    private List<AdvertisementMachine> advertisementMachineList

    List<AdvertisementMachine> getAdvertisementMachineList() {
        return advertisementMachineList
    }

    void setAdvertisementMachineList(List<AdvertisementMachine> advertisementMachineList) {
        this.advertisementMachineList = advertisementMachineList
    }

    String getFounder() {
        return founder
    }

    void setFounder(String founder) {
        this.founder = founder
    }

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    @NotNull(message = "用户名不能为空")
    @Length(min = 1, max = 64)
    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    @NotNull(message = "登录名不能为空")
    @Length(min = 1, max = 64)
    String getAuthname() {
        return authname
    }

    void setAuthname(String authname) {
        this.authname = authname
    }

    @NotNull(message = "密码不能为空")
    @Length(min = 1, max = 256)
    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    @NotNull(message = "角色ID不能为空")
    int getRoleID() {
        return roleID
    }

    void setRoleID(int roleID) {
        this.roleID = roleID
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    String getAddress() {
        return address
    }

    void setAddress(String address) {
        this.address = address
    }

    String getAddTime() {
        return addTime
    }

    void setAddTime(String addTime) {
        this.addTime = addTime
    }

    String getPhone() {
        return phone
    }

    void setPhone(String phone) {
        this.phone = phone
    }

    String getRemark() {
        return remark
    }

    void setRemark(String remark) {
        this.remark = remark
    }

}
