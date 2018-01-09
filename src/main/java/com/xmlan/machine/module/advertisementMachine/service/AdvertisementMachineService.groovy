package com.xmlan.machine.module.advertisementMachine.service

import com.google.common.collect.Lists
import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.module.advertisementMachine.dao.AdvertisementMachineDAO
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachineCount
import com.xmlan.machine.module.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2017/12/13-08:53.
 * Package: com.xmlan.machine.module.advertisementMachine.service
 */
@Service("AdvertisementMachineService")
@Transactional(readOnly = true)
class AdvertisementMachineService extends BaseService<AdvertisementMachine, AdvertisementMachineDAO> {

    static List<AdvertisementMachineCount> getMachineCountByUserID(List<User> list) {
        List<AdvertisementMachineCount> counts = Lists.newArrayList()
        list.each {
            AdvertisementMachineCount count = new AdvertisementMachineCount()
            count.id = it.id
            count.count = AdvertisementMachineCache.getMachineCount(it.id)
            counts.add(count)
        }
        return counts
    }

    AdvertisementMachine getByCodeNumber(String codeNumber) {
        return dao.getByCodeNumber(codeNumber)
    }

    // provider support
    static List<AdvertisementMachine> findForProvider(AdvertisementMachine advertisementMachine) {
        List<AdvertisementMachine> machineList = AdvertisementMachineCache.advertisementMachineList
        List<AdvertisementMachine> filteredList = Lists.newArrayList()
        machineList.each {
            if (it.userID == advertisementMachine.userID) {
                filteredList.add(it)
            }
        }
        return filteredList
    }

    static List<AdvertisementMachine> positionQuery(String minLongitude, String maxLongitude, String minLatitude, String maxLatitude) {
        List<AdvertisementMachine> list = AdvertisementMachineCache.advertisementMachineList
        // 根据经纬度排序，先排经度再排纬度。这里采用List双条件排序。
        list.sort { l, r ->
            if (l.longitude.toDouble() < r.longitude.toDouble()) {
                return -1
            } else if (l.longitude.toDouble() == r.longitude.toDouble()) {
                l.latitude.toDouble() <=> r.latitude.toDouble()
            } else {
                return 1
            }
        }
        list.each {
            if (it.longitude.toDouble() < minLongitude.toDouble()) {

            }
        }
    }

}
