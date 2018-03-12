package com.xmlan.machine.common.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 切面记录
 * <p>
 * @author Ayakura Yuki
 * @date 2017/7/24
 */
@Component
@Aspect
public class LogAspect {

    private static final Logger logger = LogManager.getLogger(LogAspect.class);

    @AfterReturning(value = "execution(* insert*(..))")
    public void inserted() {
        logger.info("新增成功。");
    }

    @AfterThrowing(value = "execution(* insert(..))")
    public void insertDenied() {
        logger.error("新增异常。");
    }

    @AfterReturning(value = "execution(* update*(..))")
    public void updated() {
        logger.info("修改成功。");
    }

    @AfterThrowing(value = "execution(* update(..))")
    public void updateDenied() {
        logger.error("修改异常。");
    }

    @AfterReturning(value = "execution(* delete*(..))")
    public void deleted() {
        logger.info("删除成功。");
    }

    @AfterThrowing(value = "execution(* delete*(..))")
    public void deleteDenied() {
        logger.error("删除异常。");
    }

}
