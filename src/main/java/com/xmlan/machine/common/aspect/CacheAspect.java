package com.xmlan.machine.common.aspect;

import net.sf.ehcache.CacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 切面刷新缓存
 *
 * Created by ayakurayuki on 2018/1/3-10:34.
 *
 * Package: com.xmlan.machine.common.aspect
 */
@Component
@Aspect
public class CacheAspect {

    private static final Logger logger = LogManager.getLogger(CacheAspect.class);

    @Autowired
    public CacheManager cacheManager;

    /**
     * 切入insert()方法
     */
    @AfterReturning("execution(* insert*(..)) ")
    public void afterInsert() {
        cacheManager.clearAll(); // 插入操作完成后执行缓存刷新
        logger.info("Cache refreshed after insert.");
    }

    /**
     * 切入update()方法
     */
    @AfterReturning("execution(* update*(..))")
    public void afterUpdate() {
        cacheManager.clearAll(); // 更新操作完成后执行缓存刷新
        logger.info("Cache refreshed after update.");
    }

    /**
     * 切入delete()方法
     */
    @AfterReturning("execution(* delete*(..))")
    public void afterDelete() {
        cacheManager.clearAll(); // 删除操作完成后执行缓存刷新
        logger.info("Cache refreshed after delete.");
    }

    /**
     * 切入passwd()方法
     */
    @AfterReturning("execution(* passwd(..))")
    public void afterPasswd() {
        cacheManager.clearAll(); // 修改密码后执行缓存刷新
        logger.info("Cache refreshed after passwd.");
    }

    /**
     * 切入chgrp()方法
     */
    @AfterReturning("execution(* chgrp(..))")
    public void afterChgrp() {
        cacheManager.clearAll(); // 修改角色后执行缓存刷新
        logger.info("Cache refreshed after chgrp.");
    }

}
