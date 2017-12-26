package com.xmlan.machine.module.advertisement.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.module.advertisement.dao.AdvertisementDAO
import com.xmlan.machine.module.advertisement.entity.Advertisement
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2017/12/13-08:57.
 * Package: com.xmlan.machine.module.advertisement.service
 */
@Service("AdvertisementService")
@Transactional(readOnly = true)
class AdvertisementService extends BaseService<Advertisement, AdvertisementDAO> {

    @Override
    int delete(Advertisement entity) {
        if (entity.id == ADMIN_ROLE_ID) {
            return DATABASE_DO_NOTHING
        }
        return super.delete(entity)
    }

}
