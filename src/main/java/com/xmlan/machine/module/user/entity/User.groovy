package com.xmlan.machine.module.user.entity

import org.apache.ibatis.type.Alias
import org.hibernate.validator.constraints.Length

import javax.validation.constraints.NotNull
import java.util.Date

/**
 * Created by ayakurayuki on 2017/12/12-10:46.
 * Package: com.xmlan.machine.module.user.entity
 */
@Alias("User")
class User {

    private int id // 用户ID
    private String username // 用户名
    private String authname // 登录账号
    private String password // 密码, 建议SHA-256 x 10
    private int roleID // 角色ID
    private String address // 地址
    private String addTime // 加入时间
    private String phone // 联系电话
    private String remark // 备注

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    @NotNull(message = "用户名不能为空")
    String getUsername() {
        return username
    }

    @Length(min = 1, max = 64)
    void setUsername(String username) {
        this.username = username
    }

    @NotNull(message = "登录名不能为空")
    String getAuthname() {
        return authname
    }

    @Length(min = 1, max = 64)
    void setAuthname(String authname) {
        this.authname = authname
    }

    @NotNull(message = "密码不能为空")
    String getPassword() {
        return password
    }

    @Length(min = 1, max = 256)
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
