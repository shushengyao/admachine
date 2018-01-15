package com.xmlan.machine.module.advertisement.service

import com.google.common.collect.Lists
import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.util.JsonUtils
import com.xmlan.machine.common.util.UploadUtils
import com.xmlan.machine.module.advertisement.dao.AdvertisementDAO
import com.xmlan.machine.module.advertisement.entity.Advertisement
import com.xmlan.machine.module.advertisement.entity.AdvertisementCount
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.servlet.http.HttpServletRequest

/**
 * Created by ayakurayuki on 2017/12/13-08:57.
 * Package: com.xmlan.machine.module.advertisement.service
 */
@Service("AdvertisementService")
@Transactional(readOnly = true)
class AdvertisementService extends BaseService<Advertisement, AdvertisementDAO> {

    int uploadMedia(String id, HttpServletRequest request) {
        def advertisement = dao.get(id)
        def jsonString = UploadUtils.uploadImages(request)
        if (jsonString == '[]') {
            return FAILURE
        }
        def json = JsonUtils.fromJsonString(jsonString, Map.class) as Map
        def list = json.get(UploadUtils.MEDIA_KEY) as List<String>
        advertisement.url = list.get(0)
        update(advertisement)
        return DONE
    }

    static List<AdvertisementCount> getAdvertisementCount(List<AdvertisementMachine> list) {
        List<AdvertisementCount> counts = Lists.newArrayList()
        list.each {
            def count = new AdvertisementCount()
            count.id = it.id
            count.count = AdvertisementCache.getAdvertisementCountByMachineID(it.id)
            counts.add(count)
        }
        return counts
    }

    List<Advertisement> findList(Advertisement advertisement) {
        dao.findList advertisement
    }

}
