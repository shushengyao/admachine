package com.xmlan.machine.common.interceptor

import com.xmlan.machine.common.util.ColorPrintUtils
import com.xmlan.machine.common.util.DateUtils
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Ayakura Yuki on 2017/8/2 0002.
 */
class URLInterceptor implements HandlerInterceptor {

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ColorPrintUtils.Println(
                ColorPrintUtils.WHITE,
                "${DateUtils.GetDateTime()} - ${request.getMethod()} - ${request.getRequestURL()}"
        )
        return true
    }

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
