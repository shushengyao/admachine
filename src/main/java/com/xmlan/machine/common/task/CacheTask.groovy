package com.xmlan.machine.common.task

import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.cache.RoleCache
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.util.CacheUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Created by ayakurayuki on 2017/12/26-12:00. <br/>
 * Package: com.xmlan.machine.common.task <br/>
 */
@Component
class CacheTask {

    private Logger logger = LogManager.getLogger(CacheTask.class)

    /**
     * 定时刷新缓存, 周期为30分钟
     * @return
     */
    @Scheduled(fixedRate = 1800000L)
    def refreshCache() {
        CacheUtils.cacheManager.clearAll()
        AdvertisementMachineCache.initialCacheMap()
        AdvertisementCache.initialCacheMap()
        UserCache.initialCacheMap()
        RoleCache.initialCacheMap()
        logger.info "Cache refreshed."
    }

}
