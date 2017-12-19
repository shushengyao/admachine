package com.xmlan.machine.module.user.entity;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ayakurayuki on 2017/12/12-10:46.
 * Package: com.xmlan.machine.module.user.entity
 */
@Alias("User")
public class User {

    private int id; // 用户ID
    private String username; // 用户名
    private String authname; // 登录账号
    private String password; // 密码, 建议SHA-256 x 10
    private int roleID; // 角色ID
    private String address; // 地址
    private Date addTime; // 加入时间
    private String phone; // 联系电话
    private String remark; // 备注

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull(message = "用户名不能为空")
    public String getUsername() {
        return username;
    }

    @Length(min = 1, max = 64)
    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "登录名不能为空")
    public String getAuthname() {
        return authname;
    }

    @Length(min = 1, max = 64)
    public void setAuthname(String authname) {
        this.authname = authname;
    }

    @NotNull(message = "密码不能为空")
    public String getPassword() {
        return password;
    }

    @Length(min = 1, max = 256)
    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "角色ID不能为空")
    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
