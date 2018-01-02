package com.xmlan.machine.module.role.entity

import org.apache.ibatis.type.Alias

/**
 * Created by ayakurayuki on 2017/12/29-11:20.
 * Package: com.xmlan.machine.module.role.entity
 */
@Alias("RoleCount")
class RoleCount implements Serializable {

    int id
    int count

}
