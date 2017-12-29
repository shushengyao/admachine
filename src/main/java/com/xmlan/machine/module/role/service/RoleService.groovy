package com.xmlan.machine.module.role.service

import com.google.common.collect.Lists
import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.RoleCache
import com.xmlan.machine.module.role.dao.RoleDAO
import com.xmlan.machine.module.role.entity.Role
import com.xmlan.machine.module.role.entity.RoleCount
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2017/12/12-11:17.
 * Package: com.xmlan.machine.module.role.service
 */
@Service("RoleService")
@Transactional(readOnly = true)
class RoleService extends BaseService<Role, RoleDAO> {

    public static final int ROLE_HAVE_SOME_USERS = -20

    @Override
    int update(Role entity) {
        if (entity.id == ADMIN_ROLE_ID) {
            return DATABASE_DO_NOTHING
        } else {
            int result = dao.update(entity)
            cacheManager.clearAll()
            return result
        }
    }

    @Override
    int delete(Role entity) {
        if (entity.id == ADMIN_ROLE_ID) {
            return DATABASE_DO_NOTHING
        }
        if (RoleCache.getUserCountInRole(entity.id) != 0) {
            return ROLE_HAVE_SOME_USERS
        }
        return super.delete(entity)
    }

    static List<RoleCount> getUserCount(List<Role> list) {
        List<RoleCount> counts = Lists.newArrayList()
        list.each {
            RoleCount roleCount = new RoleCount()
            roleCount.id = it.id
            roleCount.count = RoleCache.getUserCountInRole(it.id)
            counts.add(roleCount)
        }
        return counts
    }

}
