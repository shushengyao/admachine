package com.xmlan.machine.module.role.entity;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by ayakurayuki on 2017/12/12-11:01.
 * Package: com.xmlan.machine.module.role.entity
 */
@Alias("Role")
public class Role {

    private int id; // 角色ID
    private String name; // 角色名称
    private String remark; // 备注

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull(message = "角色名称不能为空")
    public String getName() {
        return name;
    }

    @Length(min = 1, max = 64)
    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
