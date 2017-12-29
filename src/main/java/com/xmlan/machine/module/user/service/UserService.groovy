package com.xmlan.machine.module.user.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.module.advertisementMachine.dao.AdvertisementMachineDAO
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.user.dao.UserDAO
import com.xmlan.machine.module.user.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2017/12/12-11:09.
 * Package: com.xmlan.machine.module.user.service
 */
@Service("UserService")
@Transactional(readOnly = true)
class UserService extends BaseService<User, UserDAO> {

    public static final int USER_HAVE_SOME_MACHINES = -10

    @Autowired
    private AdvertisementMachineDAO advertisementMachineDAO

    @Override
    int delete(User entity) {
        if (entity.id == ADMIN_ROLE_ID) {
            return DATABASE_DO_NOTHING
        }
        if (AdvertisementMachineCache.getUserOwnMachineCount(entity.id) != 0) {
            return USER_HAVE_SOME_MACHINES
        }
        return super.delete(entity)
    }

}
