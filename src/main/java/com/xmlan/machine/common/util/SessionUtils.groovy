package com.xmlan.machine.common.util

import com.xmlan.machine.common.config.Global
import com.xmlan.machine.module.user.entity.User

import javax.servlet.http.HttpServletRequest

/**
 * Created by ayakurayuki on 2017/12/22-16:36.
 * Package: com.xmlan.machine.common.util
 */
class SessionUtils {

    /**
     * 设置Token到session
     * @param request
     * @param token
     */
    static void setToken(HttpServletRequest request, String token) {
        request.session.setAttribute(Global.FORM_TOKEN, token)
    }

    /**
     * 获取Token
     * @param request
     * @return
     */
    static String getToken(HttpServletRequest request) {
        return request.session.getAttribute(Global.FORM_TOKEN) as String
    }

    /**
     * 移除Token
     * @param request
     */
    static void removeToken(HttpServletRequest request) {
        request.session.removeAttribute(Global.FORM_TOKEN)
    }

    /**
     * 设置登录用户到Session中
     * @param request 请求
     * @param user 登录的用户对象
     */
    static void setAdmin(HttpServletRequest request, User user) {
        request.session.setAttribute(Global.USER, user == null ? null : GetNoSecretInfoUser(user))
    }

    /**
     * 从Session中拿到用户对象
     * @param request 请求
     * @return 用户对象
     */
    static User getAdmin(HttpServletRequest request) {
        return request.session.getAttribute(Global.USER) as User
    }

    /**
     * 获取排除敏感信息的用户对象
     * @param user
     * @return
     */
    static User GetNoSecretInfoUser(User user) {
        def obj = new User()
        obj.id = user.id
        obj.username = user.username
        obj.authname = user.authname
        obj.password = StringUtils.EMPTY
        obj.roleID = user.roleID
        obj.address = StringUtils.EMPTY
        obj.addTime = user.addTime
        obj.phone = StringUtils.EMPTY
        obj.remark = user.remark
        return obj
    }

}
