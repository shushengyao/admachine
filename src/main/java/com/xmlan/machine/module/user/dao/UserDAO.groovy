package com.xmlan.machine.module.user.dao

import com.xmlan.machine.common.base.BaseDAO
import com.xmlan.machine.module.user.entity.User
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

/**
 * Created by ayakurayuki on 2017/12/12-10:51.
 * Package: com.xmlan.machine.module.user.dao
 */
@Repository
interface UserDAO extends BaseDAO<User> {

    /**
     * 修改角色
     *
     * @param user 待修改用户
     * @return 修改影响的条目数
     */
    int changeRoleID(User user)

    /**
     * 修改密码
     *
     * @param user 待修改用户
     * @return 修改影响的条目数
     */
    int changePassword(User user)

    /**
     * 登录
     *
     * @param authname 登录名
     * @param password 密码
     * @return 获取到登录用户对象
     */
    User login(@Param("authname") String authname, @Param("password") String password)

}
