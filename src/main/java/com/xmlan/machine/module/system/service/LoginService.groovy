package com.xmlan.machine.module.system.service

import com.xmlan.machine.common.util.EncryptUtils
import com.xmlan.machine.common.util.SessionUtils
import com.xmlan.machine.module.user.dao.UserDAO
import com.xmlan.machine.module.user.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.servlet.http.HttpServletRequest

/**
 * Created by ayakurayuki on 2017/12/13-15:51.
 * Package: com.xmlan.machine.module.system.service
 */
@Service("LoginService")
@Transactional(readOnly = true)
class LoginService {

    @Autowired
    private UserDAO userDAO

    /**
     * 登录方法
     *
     * @param authname 登录名
     * @param password 密码
     * @return 登录结果：true成功；false失败
     */
    boolean login(HttpServletRequest request, String authname, String password) {
        password = EncryptUtils.SHA256ForTenTimes(password)
        def user = userDAO.login(authname, password)
        if (user != null) {
            SessionUtils.setAdmin(request, user)
            return true
        } else {
            return false
        }
    }

    User loginForMobile(String authname, String password) {
        return userDAO.login(authname, password)
    }

}
