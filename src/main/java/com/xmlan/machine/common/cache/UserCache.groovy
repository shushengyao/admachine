package com.xmlan.machine.common.cache

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.xmlan.machine.common.util.CacheUtils
import com.xmlan.machine.common.util.SpringContextUtils
import com.xmlan.machine.module.user.dao.UserDAO
import com.xmlan.machine.module.user.entity.SimpleUser
import com.xmlan.machine.module.user.entity.User

/**
 * Created by ayakurayuki on 2017/12/26-11:14.
 * <p>
 * Package: com.xmlan.machine.common.cache
 */
final class UserCache {

    private static final def CACHE_NAME = "userCache"
    private static final def MAP_NAME = "userCacheMap"
    private static final def USER_LIST_NAME = "userList"

    private static def userDAO = SpringContextUtils.getBean(UserDAO.class)

    /**
     * 初始化
     * @return 初始化的map
     */
    static Map<String, List> initialCacheMap() {
        Map map = CacheUtils.get(CACHE_NAME, MAP_NAME) as Map<String, List>
        if (map == null) {
            map = Maps.newHashMap()
            List<User> userList = userDAO.findAll()
            map.put USER_LIST_NAME, userList
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
     * 获取包含完整用户信息的列表
     * @return 存储全部用户对象的列表
     */
    static List<User> getUserList() {
        return getList(USER_LIST_NAME)
    }

    /**
     * 获取下拉菜单用的简单用户列表
     * @return 简单用户列表，包括userID和username
     */
    static List<SimpleUser> getDropdownUserList() {
        List<User> userList = getList(USER_LIST_NAME)
        List<SimpleUser> simpleUserList = Lists.newArrayList()
        userList.each {
            SimpleUser bean = new SimpleUser()
            bean.id = it.id
            bean.username = it.username
            simpleUserList.add(bean)
        }
        return simpleUserList
    }

    /**
     * 根据ID获取用户
     * @param id 查询ID
     * @return
     */
    static User get(int id) {
        List<User> list = getUserList()
        for (user in list) {
            if (user.id == id) {
                return user
            }
        }
        return null
    }

}
