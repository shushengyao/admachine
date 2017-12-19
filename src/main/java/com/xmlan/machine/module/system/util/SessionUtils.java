package com.xmlan.machine.module.system.util;

import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.module.user.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by ayakurayuki on 2017/12/13-16:06.
 * Package: com.xmlan.machine.module.system.util
 */
public class SessionUtils {

    /**
     * 管理员Session标志
     */
    public static final String ADMIN_SESSION_TAG = "SYSTEM_ADMIN";

    /**
     * 登录后添加管理员到Session中，添加前清空敏感信息
     *
     * @param session 当前session
     * @param user    登录用户
     */
    public static void SetAdmin(HttpSession session, User user) {
        session.setAttribute(ADMIN_SESSION_TAG, CleanLoginInfo(user));
    }

    /**
     * 获取登录的用户对象，这是不完整的对象，需要重新查询获取完整的信息
     *
     * @param session 当前session
     * @return 用户对象
     */
    public static User GetAdmin(HttpSession session) {
        return (User) session.getAttribute(ADMIN_SESSION_TAG);
    }

    /**
     * 创建一个不包含敏感信息的新对象
     *
     * @param user 数据库获取的对象
     * @return 没有敏感信息的对象
     */
    private static User CleanLoginInfo(User user) {
        User userWithoutLoginInfo = new User();
        // 保留
        userWithoutLoginInfo.setId(user.getId());
        userWithoutLoginInfo.setUsername(user.getUsername());
        // 清空（重点）
        userWithoutLoginInfo.setAuthname(StringUtils.EMPTY);
        userWithoutLoginInfo.setPassword(StringUtils.EMPTY);
        // 保留
        userWithoutLoginInfo.setRoleID(user.getRoleID());
        // 清空
        userWithoutLoginInfo.setAddress(StringUtils.EMPTY);
        userWithoutLoginInfo.setAddTime(new Date(0));
        userWithoutLoginInfo.setPhone(StringUtils.EMPTY);
        // 保留
        userWithoutLoginInfo.setRemark(user.getRemark());
        return userWithoutLoginInfo;
    }

}
