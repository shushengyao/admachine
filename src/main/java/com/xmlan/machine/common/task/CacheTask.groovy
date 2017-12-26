package com.xmlan.machine.common.task

import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.util.CacheUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Created by ayakurayuki on 2017/12/26-12:00.
 * Package: com.xmlan.machine.common.task
 */
@Component
class CacheTask {

    private Logger logger = LogManager.getLogger(CacheTask.class)

    @Scheduled(fixedRate = 1800000L)
    def refreshCache() {
        CacheUtils.cacheManager.clearAll()
        UserCache.initialCacheMap()
        AdvertisementCache.initialCacheMap()
        logger.trace "Cache refreshed."
    }

}
