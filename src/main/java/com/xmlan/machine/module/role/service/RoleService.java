package com.xmlan.machine.module.role.service;

import com.xmlan.machine.common.base.BaseService;
import com.xmlan.machine.module.role.dao.RoleDAO;
import com.xmlan.machine.module.role.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ayakurayuki on 2017/12/12-11:17.
 * Package: com.xmlan.machine.module.role.service
 */
@Service("RoleService")
@Transactional(readOnly = true)
public class RoleService extends BaseService<Role, RoleDAO> {

    @Override
    public int delete(Role entity) {
        if (entity.getId() == 1) {
            return DATABASE_DO_NOTHING;
        }
        return super.delete(entity);
    }

}
