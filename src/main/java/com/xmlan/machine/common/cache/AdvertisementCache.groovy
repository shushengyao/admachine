package com.xmlan.machine.common.cache

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.xmlan.machine.common.util.CacheUtils
import com.xmlan.machine.common.util.SpringContextUtils
import com.xmlan.machine.module.advertisement.dao.AdvertisementDAO
import com.xmlan.machine.module.advertisement.entity.Advertisement

/**
 * Created by ayakurayuki on 2017/12/26-14:28.
 * Package: com.xmlan.machine.common.cache
 */
class AdvertisementCache {

    private static final def CACHE_NAME = "advertisementCache"
    private static final def MAP_NAME = "adCacheMap"
    private static final def AD_LIST_NAME = "advertisementList"

    private static def advertisementDAO = SpringContextUtils.getBean(AdvertisementDAO.class)

    /**
     * 初始化
     * @return 初始化的map
     */
    static Map<String, List> initialCacheMap() {
        Map map = CacheUtils.get(CACHE_NAME, MAP_NAME) as Map<String, List>
        if (map == null) {
            map = Maps.newHashMap()
            List<Advertisement> advertisementList = advertisementDAO.findAll()
            map.put AD_LIST_NAME, advertisementList
        }
        return map
    }

    static List getList(String key) {
        Map<String, List> map = initialCacheMap()
        List list = map.get(key)
        if (list == null) {
            list = Lists.newArrayList()
        }
        return list
    }

    /**
     * 获取完整的广告列表
     * @return 完整的广告列表
     */
    static List<Advertisement> getAdvertisementList() {
        return getList(AD_LIST_NAME)
    }

    /**
     * 根据ID获取对象
     * @param id
     * @return
     */
    static Advertisement get(int id) {
        List<Advertisement> list = advertisementList
        for (advertisement in list) {
            if (advertisement.id == id) {
                return advertisement
            }
        }
        return null
    }

}
