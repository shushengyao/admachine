package com.xmlan.machine.common.util;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 从ApplicationContext获取已注册的Bean实例
 *
 * @author Ayakura Yuki
 * @date 2017/7/11
 */
public class SpringContextUtils implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringContextUtils.class);

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * Check ApplicationContext is not null.
     */
    private static void assertContextInjected() {
        Validate.validState(applicationContext != null,
                "applicationContext属性未注入, 请在spring-*.xml中定义SpringContextHolder.");
    }

    @Override
    public void destroy() {
        if (logger.isDebugEnabled()) {
            logger.debug("清除SpringContextHolder中的ApplicationContext: " + applicationContext);
        }
        applicationContext = null;
    }

}
