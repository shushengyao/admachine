package com.xmlan.machine.common.cache

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.xmlan.machine.common.util.CacheUtils
import com.xmlan.machine.common.util.SpringContextUtils
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.role.dao.RoleDAO
import com.xmlan.machine.module.role.entity.Role
import com.xmlan.machine.module.user.entity.User

/**
 * Created by ayakurayuki on 2017/12/27-16:11.
 * Package: com.xmlan.machine.common.cache
 */
class RoleCache {

    private static final def CACHE_NAME = "userCache"
    private static final def MAP_NAME = "roleCacheMap"
    private static final def ROLE_LIST_NAME = "roleList"

    private static def roleDAO = SpringContextUtils.getBean(RoleDAO.class)

    static Map<String, List> initialCacheMap() {
        Map map = CacheUtils.get(CACHE_NAME, MAP_NAME) as Map<String, List>
        if (map == null) {
            map = Maps.newHashMap()
            List<Role> roleList = roleDAO.findAll()
            map.put ROLE_LIST_NAME, roleList
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

    static List<Role> getRoleList() {
        return getList(ROLE_LIST_NAME)
    }

    static Role get(String id) {
        List<Role> list = getRoleList()
        for (role in list) {
            if (StringUtils.equals(role.id.toString(), id)) {
                return role
            }
        }
        return null
    }

    /**
     * 获取角色拥有的用户数量
     * @param roleID
     * @return
     */
    static int getUserCountInRole(int roleID) {
        List<User> list = UserCache.getUserList()
        int count = 0
        list.forEach({
            if (it.roleID == roleID) {
                count++
            }
        })
        return count
    }

}
