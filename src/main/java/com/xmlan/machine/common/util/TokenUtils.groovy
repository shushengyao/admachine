package com.xmlan.machine.common.util

import com.google.common.collect.Maps
import com.xmlan.machine.common.config.Global
import com.xmlan.machine.module.system.service.LoginService
import com.xmlan.machine.module.user.entity.User
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import javax.servlet.http.HttpServletRequest

/**
 * Created by ayakurayuki on 2018/1/15-10:47. <br/>
 * Package: com.xmlan.machine.common.util <br/>
 */
final class TokenUtils {

    private static Logger logger = LogManager.getLogger(TokenUtils.class)
    private static String authname = 'authname'
    private static String password = 'password'

    private static LoginService loginService = SpringContextUtils.getBean(LoginService.class)

    /**
     * 获取Token
     * @param auth 登录名
     * @param pass 密码
     * @return Token字串
     */
    static String getToken(String auth, String pass) {
        def info = Maps.newHashMap()
        info[authname] = auth
        info[password] = pass
        return EncodeUtils.encodeBase64(JsonUtils.toJsonString(info))
    }

    static boolean validateToken(String token) {
        def decode = EncodeUtils.decodeBase64String(token)
        def info = JsonUtils.fromJsonString(decode, HashMap.class) as HashMap<String, String>
        def user = loginService.loginForMobile(info[authname], info[password])
        return user != null
    }

    static User validateTokenGetUser(String token) {
        def decode = EncodeUtils.decodeBase64String(token)
        def info = JsonUtils.fromJsonString(decode, HashMap.class) as HashMap<String, String>
        return loginService.loginForMobile(info[authname], info[password])
    }

    /**
     * 表单验证：获取Token
     * @param request
     * @return
     */
    static String getFormToken(HttpServletRequest request) {
        return getFormToken(request, Global.FORM_TOKEN)
    }

    /**
     * 获取Token
     * @param request
     * @param flag
     * @return
     */
    static String getFormToken(HttpServletRequest request, String flag) {
        def characteristic = IDUtils.randomBase62(10)
        def token = EncodeUtils.encodeBase64(characteristic)
        SessionUtils.setFormToken(request, flag, token)
        logger.trace token
        return token
    }

    /**
     * 表单验证：验证Token
     * @param request
     * @param token
     * @return
     */
    static boolean validateFormToken(HttpServletRequest request, String token) {
        validateFormToken(request, Global.FORM_TOKEN, token)
    }

    /**
     * 表单验证：验证Token
     * @param request
     * @param flag
     * @param token
     * @return
     */
    static boolean validateFormToken(HttpServletRequest request, String flag, String token) {
        def tokenInSession = SessionUtils.getFormToken(request, flag)
        if (token != tokenInSession) {
            SessionUtils.removeFormToken(request, flag)
            return false
        } else {
            SessionUtils.removeFormToken(request, flag)
            return true
        }
    }

}
