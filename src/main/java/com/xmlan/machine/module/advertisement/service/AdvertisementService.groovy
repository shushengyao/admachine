package com.xmlan.machine.module.advertisement.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.util.JsonUtils
import com.xmlan.machine.common.util.UploadUtils
import com.xmlan.machine.module.advertisement.dao.AdvertisementDAO
import com.xmlan.machine.module.advertisement.entity.Advertisement
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
        Advertisement advertisement = dao.get(id)
        String jsonString = UploadUtils.uploadImages(request)
        if (jsonString == '[]') {
            return FAILURE
        }
        def json = JsonUtils.fromJsonString(jsonString, Map.class) as Map
        def list = json.get(UploadUtils.MEDIA_KEY) as List<String>
        advertisement.url = list.get(0)
        update(advertisement)
        return DONE
    }

}
