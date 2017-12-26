package com.xmlan.machine.common.util

import net.sf.ehcache.Cache
import net.sf.ehcache.CacheManager
import net.sf.ehcache.Element

/**
 * Created by Ayakura Yuki on 2017/7/11.<br/>
 * 缓存操作工具类
 */
class CacheUtils {

    private static CacheManager cacheManager = SpringContextUtils.getBean("cacheManager")

    private static final String SYS_CACHE = "sysCache"

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    static Object get(String key) {
        return get(SYS_CACHE, key)
    }

    /**
     * 写入SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    static void put(String key, Object value) {
        put SYS_CACHE, key, value
    }

    /**
     * 从SYS_CACHE缓存中移除
     *
     * @param key
     * @return
     */
    static void remove(String key) {
        remove SYS_CACHE, key
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    static Object get(String cacheName, String key) {
        Element element = getCache(cacheName).get(key)
        return element == null ? null : element.getObjectValue()
    }

    /**
     * 写入缓存
     *
     * @param cacheName
     * @param key
     * @param value
     */
    static void put(String cacheName, String key, Object value) {
        Element element = new Element(key, value)
        getCache(cacheName).put(element)
    }

    /**
     * 从缓存中移除
     *
     * @param cacheName
     * @param key
     */
    static void remove(String cacheName, String key) {
        getCache(cacheName).remove(key)
    }

    /**
     * 获得一个Cache，没有则创建一个。
     *
     * @param cacheName
     * @return
     */
    private static Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName)
        if (cache == null) {
            cacheManager.addCache(cacheName)
            cache = cacheManager.getCache(cacheName)
            cache.getCacheConfiguration().setEternal(true)
        }
        return cache
    }

    static CacheManager getCacheManager() {
        return cacheManager
    }

}
