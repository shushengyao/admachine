package com.xmlan.machine.module.user.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.module.user.dao.UserDAO
import com.xmlan.machine.module.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2017/12/12-11:09.
 * Package: com.xmlan.machine.module.user.service
 */
@Service("UserService")
@Transactional(readOnly = true)
class UserService extends BaseService<User, UserDAO> {

    @Override
    int delete(User entity) {
        if (entity.id == 1) {
            return DATABASE_DO_NOTHING
        }
        return super.delete(entity)
    }

}
