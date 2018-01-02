package com.xmlan.machine.module.user.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.util.CacheUtils
import com.xmlan.machine.common.util.EncryptUtils
import com.xmlan.machine.common.util.StringUtils
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
    int insert(User entity) {
        entity.password = EncryptUtils.SHA256ForTenTimes(entity.password)
        return super.insert(entity)
    }

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

    int passwd(String id, String oldPasswd, String newPasswd, String rePasswd) {
        User user = dao.get(id)
        if (StringUtils.equals(newPasswd, rePasswd)) {
            if (user.roleID == 1) {
                if (StringUtils.equals(user.password, oldPasswd)) {
                    user.password = EncryptUtils.SHA256ForTenTimes(newPasswd)
                    dao.changePassword(user)
                    cacheManager.clearAll()
                    return ADMIN_DONE
                } else {
                    return INCORRECT_OLDPASSWD
                }
            } else {
                user.password = EncryptUtils.SHA256ForTenTimes(newPasswd)
                dao.changePassword(user)
                cacheManager.clearAll()
                return DONE
            }
        } else {
            return INCORRECT_REPASSWD
        }
    }

}
