package com.xmlan.machine.common.interceptor

import com.xmlan.machine.common.util.ColorPrintUtils
import com.xmlan.machine.common.util.DateUtils
import org.apache.logging.log4j.LogManager
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * URL访问查看 <br/>
 * Created by Ayakura Yuki on 2017/8/2-00:02. <br/>
 */
class URLInterceptor implements HandlerInterceptor {

    private static def logger = LogManager.getLogger(URLInterceptor.class)

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.trace(ColorPrintUtils.Format(
                ColorPrintUtils.PURPLE,
                "%-26s%-7s%s",
                DateUtils.GetDate('yyyy-MM-dd HH:mm:ss.SSS'),
                request.method,
                request.requestURL
        ))
        return true
    }

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
