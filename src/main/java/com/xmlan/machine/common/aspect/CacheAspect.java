package com.xmlan.machine.common.aspect;

import com.xmlan.machine.common.util.CacheUtils;
import net.sf.ehcache.CacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 切面刷新缓存
 * Created by ayakurayuki on 2018/1/3-10:34.
 * Package: com.xmlan.machine.common.aspect
 */
@Component
@Aspect
public class CacheAspect {

    private static final Logger logger = LogManager.getLogger(CacheAspect.class);

    @Autowired
    public CacheManager cacheManager;

    @AfterReturning("execution(* insert*(..)) ")
    public void afterInsert() {
        cacheManager.clearAll();
        logger.info("Cache refreshed after insert.");
    }

    @AfterReturning("execution(* update*(..))")
    public void afterUpdate() {
        cacheManager.clearAll();
        logger.info("Cache refreshed after update.");
    }

    @AfterReturning("execution(* delete*(..))")
    public void afterDelete() {
        cacheManager.clearAll();
        logger.info("Cache refreshed after delete.");
    }

    @AfterReturning("execution(* passwd(..))")
    public void afterPasswd() {
        cacheManager.clearAll();
        logger.info("Cache refreshed after passwd.");
    }

}
