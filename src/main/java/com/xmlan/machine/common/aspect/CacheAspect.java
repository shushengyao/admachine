package com.xmlan.machine.common.aspect;

import net.sf.ehcache.CacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Ayakura Yuki on 2017/7/24.
 */
@Component
@Aspect
public class CacheAspect {

    private static final Logger logger = LogManager.getLogger(CacheAspect.class);

    private final CacheManager cacheManager;

    @Autowired
    public CacheAspect(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    //
    //    @After(value = "execution(* com.xmlan.machine.module.user..*.insert*(..))" +
    //            "|| execution(* com.xmlan.machine.module.order..*.insert*(..))" +
    //            "|| execution(* com.xmlan.machine.module.carrier..*.insert*(..))" +
    //            "|| execution(* com.xmlan.machine.module.goods..*.insert*(..))")
    //    public void doClearCacheWhenInsert() {
    //        cacheManager.clearAll();
    //        logger.debug("Cache reset after inserted.");
    //    }
    //
    //    @After(value = "execution(* com.xmlan.machine.module.user..*.update*(..))" +
    //            "|| execution(* com.xmlan.machine.module.order..*.update*(..))" +
    //            "|| execution(* com.xmlan.machine.module.carrier..*.update*(..))" +
    //            "|| execution(* com.xmlan.machine.module.goods..*.update*(..))")
    //    public void doClearCacheWhenUpdate() {
    //        cacheManager.clearAll();
    //        logger.debug("Cache reset after updated.");
    //    }
    //
    //    @After(value = "execution(* com.xmlan.machine.module.user..*.delete*(..))" +
    //            "|| execution(* com.xmlan.machine.module.order..*.delete*(..))" +
    //            "|| execution(* com.xmlan.machine.module.carrier..*.delete*(..))" +
    //            "|| execution(* com.xmlan.machine.module.goods..*.delete*(..))")
    //    public void doClearCacheWhenDelete() {
    //        cacheManager.clearAll();
    //        logger.debug("Cache reset after deleted.");
    //    }

}
