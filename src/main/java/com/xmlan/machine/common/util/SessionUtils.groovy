package com.xmlan.machine.common.util

import com.xmlan.machine.common.config.Global
import com.xmlan.machine.module.user.entity.User

import javax.servlet.http.HttpServletRequest

/**
 * Created by ayakurayuki on 2017/12/22-16:36.
 * Package: com.xmlan.machine.common.util
 */
class SessionUtils {

    static void SetAdmin(HttpServletRequest request, User user) {
        request.getSession().setAttribute(Global.USER, user == null ? null : GetNoSecretInfoUser(user))
    }

    static User GetAdmin(HttpServletRequest request) {
        return request.session.getAttribute(Global.USER) as User
    }

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
