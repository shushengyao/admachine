package com.xmlan.machine.common.cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.util.CacheUtils;
import com.xmlan.machine.common.util.SpringContextUtils;
import com.xmlan.machine.module.led_machine.dao.Led_machineDAO;
import com.xmlan.machine.module.led_machine.entity.Led_machine;
import com.xmlan.machine.module.led_machine.entity.SimpleLed;
import com.xmlan.machine.module.user.entity.SimpleUser;

import java.util.List;
import java.util.Map;

/**
 * led查询缓存
 * @program: admachine
 * @description: cache
 * @author: YSS
 * @create: 2018-12-04 09:58
 **/
final class LedCache {
    private static final String CACHE_NAME = "ledCache";
    private static final String MAP_NAME = "ledCacheMap";
    private static final String LED_LIST_NAME = "ledList";

    private static Led_machineDAO userDAO = SpringContextUtils.getBean(Led_machineDAO.class);

    /**
     * 初始化
     * @return
     */
    static Map<String,List> initialCacheMap(){
        Map<String,List> map = (Map<String, List>) CacheUtils.get(CACHE_NAME, MAP_NAME);
        if (map == null) {
            map = Maps.newHashMap();
            List<Led_machine> list = userDAO.findAll();
            map.put( LED_LIST_NAME, list);
        }
        CacheUtils.put(CACHE_NAME, MAP_NAME, map);
        return map;
    }
    /**
     * 获取list
     */
    static List getList(String key) {
        Map<String, List> map = initialCacheMap();
        List list = map.get(key);
        if (list == null) {
            list = Lists.newArrayList();
        }
        return list;
    }

    /**
     * 获取包含完整用户信息的列表
     * @return 存储全部用户对象的列表
     */
    static List<Led_machine> getLedList() {
        return getList(LED_LIST_NAME);
    }

    /**
     * 获取下拉菜单用的简单用户列表
     * @return 简单用户列表，包括userID和username
     */
    static List<SimpleLed> getDropdownUserList() {
        List<Led_machine> userList = getList(LED_LIST_NAME);
        List<SimpleLed> simpleUserList = Lists.newArrayList();
        Led_machine led_machine = new Led_machine();
        userList.forEach(item -> {
            SimpleLed bean = new SimpleLed();
            bean.setId(led_machine.getId());
            bean.setName(led_machine.getName());
            bean.setLed(led_machine.getLed());
            simpleUserList.add(bean);
        });
        return simpleUserList;
    }

    /**
     * 根据ID获取用户
     * @param id 查询ID
     * @return
     */
    static Led_machine get(int id) {
        List<Led_machine> list = getLedList();
        for (Led_machine led : list) {
            if (led.getId() == id) {
                return led;
            }
        }
        return null;
    }

}
