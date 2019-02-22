package com.xmlan.machine.module.user.dao

import com.xmlan.machine.common.base.BaseDAO
import com.xmlan.machine.module.user.entity.User
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestParam

/**
 * yss
 * 用户dao
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
     * 根据创建人查询用户列表
     * @param founder
     * @return
     */
    List<User> findListByFounder(@Param("founder") String founder)

    /**
     * 修改密码
     *
     * @param user 待修改用户
     * @return 修改影响的条目数
     */
    int changePassword(User user)
    /**
     * 根据使用者id查询创建者name
     * @param userID
     * @return
     */
    List<User> findListByUserID(@RequestParam("userID") int userID)
    /**
     * 查询所有
     * @return
     */
    List<User> findAll()
    /**
     * 根据创建人查询使用者id列表
     * @param username
     * @return
     */
    List<User> findUserIDByUsername(@RequestParam("username") String username)

    /**
     * 登录
     *
     * @param authname 登录名
     * @param password 密码
     * @return 获取到登录用户对象
     */
    User login(@Param("authname") String authname, @Param("password") String password)

}
