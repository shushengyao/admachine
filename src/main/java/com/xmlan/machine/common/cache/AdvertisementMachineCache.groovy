package com.xmlan.machine.common.cache

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.xmlan.machine.common.util.CacheUtils
import com.xmlan.machine.common.util.SpringContextUtils
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.advertisementMachine.dao.AdvertisementMachineDAO
import com.xmlan.machine.module.advertisementMachine.dao.MachineSensorDAO
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor
import com.xmlan.machine.module.advertisementMachine.entity.SimpleAdvertisementMachine

/**
 * Created by ayakurayuki on 2017/12/29-10:35.
 * <p>
 * Package: com.xmlan.machine.common.cache
 */
final class AdvertisementMachineCache {

    private static final def CACHE_NAME = "advertisementMachineCache"
    private static final def MAP_NAME = "adMachineCacheMap"
    private static final def AD_MACHINE_LIST_NAME = "advertisementMachineList"
    private static final def MACHINE_SENSOR_LIST_NAME = "machineSensorList"

    private static def advertisementMachineDAO = SpringContextUtils.getBean(AdvertisementMachineDAO.class)
    private static def machineSensorDAO = SpringContextUtils.getBean(MachineSensorDAO.class)

    /**
     * 初始化
     * @return 初始化的map
     */
    static Map<String, List> initialCacheMap() {
        Map map = CacheUtils.get(CACHE_NAME, MAP_NAME) as Map<String, List>
        if (map == null) {
            map = Maps.newHashMap()
            List<AdvertisementMachine> advertisementMachineList = advertisementMachineDAO.findAll()
            map.put AD_MACHINE_LIST_NAME, advertisementMachineList
            List<MachineSensor> machineSensorList = machineSensorDAO.findAll()
            map.put MACHINE_SENSOR_LIST_NAME, machineSensorList
        }
        CacheUtils.put(CACHE_NAME, MAP_NAME, map)
        return map
    }

    /**
     * 获取缓存中的list
     * @param key
     * @return
     */
    static List getList(String key) {
        Map<String, List> map = initialCacheMap()
        List list = map.get(key)
        if (list == null) {
            list = Lists.newArrayList()
        }
        return list
    }

    /**
     * 获取完整的广告机列表
     * @return 完整的广告机列表
     */
    static List<AdvertisementMachine> getAdvertisementMachineList() {
        return getList(AD_MACHINE_LIST_NAME)
    }

    /**
     * 获取完整的传感器信息列表
     * @return 完整的传感器信息列表
     */
    static List<MachineSensor> getMachineSensorList() {
        return getList(MACHINE_SENSOR_LIST_NAME)
    }

    /**
     * 获取用户拥有的广告机数量
     * @param userID 用户ID
     * @return
     */
    static int getMachineCount(int userID) {
        List<AdvertisementMachine> list = advertisementMachineList
        int count = 0
        list.forEach({
            if (it.userID == userID) {
                count++
            }
        })
        return count
    }

    /**
     * 管理员获取总的广告机数量
     * @return
     */
    static int getMachineCount() {
        List<AdvertisementMachine> list = advertisementMachineList
        return list.size()
    }

    /**
     * 获取下拉菜单的广告机列表
     * @return
     */
    static List<SimpleAdvertisementMachine> getDropdownAdvertisementMachineList() {
        List<AdvertisementMachine> advertisementMachines = advertisementMachineList
        List<SimpleAdvertisementMachine> simpleAdvertisementMachines = Lists.newArrayList()
        advertisementMachines.each {
            def simpleADMachine = new SimpleAdvertisementMachine()
            simpleADMachine.id = it.id
            simpleADMachine.name = it.name
            simpleAdvertisementMachines.add(simpleADMachine)
        }
        return simpleAdvertisementMachines
    }

    /**
     * 根据ID获取名称
     * @param id 广告机ID
     * @return
     */
    static String getMachineNameByID(int id) {
        List<AdvertisementMachine> list = advertisementMachineList
        for (item in list) {
            if (item.id == id) {
                return item.name
            }
        }
        return StringUtils.EMPTY
    }

    /**
     * 根据机器码获取广告机
     * @param codeNumber 机器码
     * @return
     */
    static AdvertisementMachine get(String codeNumber) {
        List<AdvertisementMachine> list = advertisementMachineList
        for (item in list) {
            if (item.codeNumber == codeNumber) {
                return item
            }
        }
        return null
    }

    /**
     * 根据ID获取广告机
     * @param id 广告机ID
     * @return
     */
    static AdvertisementMachine get(int id) {
        def list = advertisementMachineList
        for (item in list) {
            if (item.id == id) {
                return item
            }
        }
        return null
    }

    /**
     * 获取传感器对象
     * @param machineID 广告机ID
     * @return
     */
    static MachineSensor getSensorInfo(int machineID) {
        def list = machineSensorList
        for (item in list) {
            if (item.machineID == machineID.toString()) {
                return item
            }
        }
        return null
    }

}
