package com.xmlan.machine.module.advertisementMachine.service

import com.google.common.collect.Lists
import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.module.advertisementMachine.dao.AdvertisementMachineDAO
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachineCount
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor
import com.xmlan.machine.module.user.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2017/12/13-08:53.
 * Package: com.xmlan.machine.module.advertisementMachine.service
 */
@Service("AdvertisementMachineService")
@Transactional(readOnly = true)
class AdvertisementMachineService extends BaseService<AdvertisementMachine, AdvertisementMachineDAO> {

    private static final String longitude = "longitude"
    private static final String latitude = "latitude"

    @Autowired
    private MachineSensorService machineSensorService

    private static boolean isInArea(String flag, String value, LinkedHashMap<String, String> area) {
        switch (flag) {
            case longitude:
                return value.toDouble() >= area.minLon.toDouble() && value.toDouble() <= area.maxLon.toDouble()
            case latitude:
                return value.toDouble() >= area.minLat.toDouble() && value.toDouble() <= area.maxLat.toDouble()
            default:
                return false
        }
    }

    private static boolean checkOperate(int operate) {
        return operate == 0 ? true : operate == 1
    }

    @Override
    int insert(AdvertisementMachine machine) {
        def responseCode = super.insert(machine)
        def sensorData = new MachineSensor()
        sensorData.machineID = machine.id
        machineSensorService.insert(sensorData)
        return responseCode
    }

    @Override
    int delete(AdvertisementMachine machine) {
        def sensorData = machineSensorService.getByMachineID(machine.id.toString())
        machineSensorService.delete(sensorData)
        return super.delete(machine)
    }

    AdvertisementMachine getByCodeNumber(String codeNumber) {
        return dao.getByCodeNumber(codeNumber)
    }

    int lightControl(int id, int operate) {
        if (null == AdvertisementMachineCache.get(id)) {
            return NO_SUCH_ROW
        }
        if (!checkOperate(operate)) {
            return ERROR_REQUEST
        }
        def machine = AdvertisementMachineCache.get(id)
        machine.light = operate
        dao.lightControl(id, operate)
        return DONE
    }

    int chargeControl(int id, int operate) {
        if (null == AdvertisementMachineCache.get(id)) {
            return NO_SUCH_ROW
        }
        if (!checkOperate(operate)) {
            return ERROR_REQUEST
        }
        def machine = AdvertisementMachineCache.get(id)
        machine.charge = operate
        dao.chargeControl(id, operate)
        return DONE
    }

    int checkedControl(int id, int operate) {
        if (null == AdvertisementMachineCache.get(id)) {
            return NO_SUCH_ROW
        }
        if (!checkOperate(operate)) {
            return ERROR_REQUEST
        }
        def machine = AdvertisementMachineCache.get(id)
        machine.checked = operate
        dao.checkedControl(id, operate)
        return DONE
    }

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

    /**
     * 根据两点确定范围内的广告机位置
     * @param minLon min longitude
     * @param maxLon max longitude
     * @param minLat min latitude
     * @param maxLat max latitude
     * @return 返回过滤后的广告机列表
     */
    static List<AdvertisementMachine> positionQuery(String minLon, String maxLon, String minLat, String maxLat) {
        List<AdvertisementMachine> list = AdvertisementMachineCache.advertisementMachineList
        // 根据范围条件过滤选中广告机。
        List<AdvertisementMachine> filteredList = Lists.newArrayList()
        list.each {
            def area = ["minLon": minLon, "minLat": minLat, "maxLon": maxLon, "maxLat": maxLat]
            if (isInArea(longitude, it.longitude, area)) {
                if (isInArea(latitude, it.latitude, area)) {
                    filteredList.add(it)
                }
            }
        }
        // 根据经纬度排序，先排经度再排纬度。这里采用List双条件排序。
        filteredList.sort { l, r ->
            if (l.longitude.toDouble() < r.longitude.toDouble()) {
                return -1
            } else if (l.longitude.toDouble() == r.longitude.toDouble()) {
                l.latitude.toDouble() <=> r.latitude.toDouble()
            } else {
                return 1
            }
        }
        return filteredList
    }

}
