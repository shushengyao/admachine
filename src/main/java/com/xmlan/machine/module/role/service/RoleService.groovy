package com.xmlan.machine.module.role.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.module.role.dao.RoleDAO
import com.xmlan.machine.module.role.entity.Role
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2017/12/12-11:17.
 * Package: com.xmlan.machine.module.role.service
 */
@Service("RoleService")
@Transactional(readOnly = true)
class RoleService extends BaseService<Role, RoleDAO> {

    @Override
    int update(Role entity) {
        if (entity.id == ADMIN_ROLE_ID) {
            return DATABASE_DO_NOTHING
        } else {
            logger.trace "${this.class.name}: update(${entity.toString()})."
            int result = dao.update(entity)
            cacheManager.clearAll()
            return result
        }
    }

    @Override
    int delete(Role entity) {
        if (entity.id == ADMIN_ROLE_ID) {
            return DATABASE_DO_NOTHING
        } else {
            return super.delete(entity)
        }
    }

}
