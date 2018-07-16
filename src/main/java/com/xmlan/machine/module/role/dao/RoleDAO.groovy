package com.xmlan.machine.module.role.dao

import com.xmlan.machine.common.base.BaseDAO
import com.xmlan.machine.module.role.entity.Role
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

/**
 * Created by ayakurayuki on 2017/12/12-11:07.
 * Package: com.xmlan.machine.module.role.dao
 */
@Repository
interface RoleDAO extends BaseDAO<Role> {
    /**
     * 根据id查角色
     * @param id
     * @return
     */
    Role findRoleById(@Param("id") int id)
}
