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
 * Package: com.xmlan.machine.common.cache
 */
class UserCache {

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
        return map
    }

    /**
     * 根据key获取缓存中的列表
     * @param key 定义的key name
     * @return 相应的List对象
     */
    private static List getList(String key) {
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

}
