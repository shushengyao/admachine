package com.xmlan.machine.common.cache

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.xmlan.machine.common.util.CacheUtils
import com.xmlan.machine.common.util.SpringContextUtils
import com.xmlan.machine.module.machineGroup.dao.MachineGroupDAO
import com.xmlan.machine.module.machineGroup.entity.MachineGroup
import com.xmlan.machine.module.machineGroup.entity.SimpleMachineGroup;

/**
 * @program: admachine
 * @description: cache
 * @author: YSS
 * @create: 2018-11-06 13:01
 **/
final class MachineGroupCache {
    private static final def CACHE_NAME = "machineGroupCache"
    private static final def MAP_NAME = "machineGroupCacheMap"
    private static final def MACHINE_GROUP_NAME = "machineGroupList"
    private static def machineGroupDAO = SpringContextUtils.getBean(MachineGroupDAO.class)

    static Map<String, List> initialCacheMap() {
        Map map = CacheUtils.get(CACHE_NAME, MAP_NAME) as Map<String, List>
        if (map == null) {
            map = Maps.newHashMap()
            List<MachineGroup> machineGroupList = machineGroupDAO.findAll()
            map.put(MACHINE_GROUP_NAME,machineGroupList)
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
     * 获取完整的分组列表
     * @return
     */
    static List<MachineGroup> getMachineGroupList(){
        return getList(MACHINE_GROUP_NAME)
    }


    /**
     * 获取下拉菜单的分组名称列表
     * @return
     */
    static List<SimpleMachineGroup> getDropdownMachineGroup(){
        List<MachineGroup> machineGroups =machineGroupList
        List <SimpleMachineGroup> simpleMachineGroups = Lists.newArrayList()
        machineGroups.each {
            def sim = new SimpleMachineGroup()
            sim.id = machineGroups.id
            sim.groupName = machineGroups.groupName
            simpleMachineGroups.add(sim)
        }
        return simpleMachineGroups
    }
}
