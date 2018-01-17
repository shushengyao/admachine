package com.xmlan.machine.common.interceptor

import com.xmlan.machine.common.config.Global
import com.xmlan.machine.common.util.SessionUtils
import com.xmlan.machine.module.user.entity.User
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by ayakurayuki on 2017/12/22-14:42. <br/>
 * Package: com.xmlan.machine.common.interceptor <br/>
 */
class LoginInterceptor implements HandlerInterceptor {

    @Override
    boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        User user = SessionUtils.getAdmin(httpServletRequest)
        if (user != null) {
            return true
        }
        httpServletResponse.sendRedirect "${Global.adminPath}/login"
        return false
    }

    @Override
    void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
