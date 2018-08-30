package com.xmlan.machine.module.user.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.cache.RoleCache
import com.xmlan.machine.common.util.EncryptUtils
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.advertisementMachine.dao.AdvertisementMachineDAO
import com.xmlan.machine.module.user.dao.UserDAO
import com.xmlan.machine.module.user.entity.User
import org.apache.ibatis.annotations.Param
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestParam

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
    List<User> findListByFounder(@Param("founder")String founder){
        dao.findListByFounder(founder)
    }

    /**
     * 根据id查询创建者
     * @param userID
     * @return
     */
    List<User> findListByUserID(@RequestParam("userID") int userID){
        dao.findListByUserID(userID)
    }
    /**
     * 根据创建者查询用户id列表
     * @param username
     * @return
     */
    List<User> findUserIDByUsername(String username){
        dao.findUserIDByUsername(username)
    }
    @Override
    int delete(User entity) {
        if (entity.id == ADMIN_ROLE_ID) {
            return DATABASE_DO_NOTHING
        }
        if (AdvertisementMachineCache.getMachineCount(entity.id) != 0) {
            return USER_HAVE_SOME_MACHINES
        }
        return super.delete(entity)
    }

    int passwd(String id, String oldPasswd, String newPasswd, String rePasswd) {
        User user = dao.get(id)
        if (StringUtils.equals(newPasswd, rePasswd)) {
            if (user.roleID == 1) {
                if (StringUtils.equals(user.password, EncryptUtils.SHA256ForTenTimes(oldPasswd))) {
                    user.password = EncryptUtils.SHA256ForTenTimes(newPasswd)
                    dao.changePassword(user)
                    return ADMIN_DONE
                } else {
                    return INCORRECT_OLD_PASSWD
                }
            } else {
                user.password = EncryptUtils.SHA256ForTenTimes(newPasswd)
                dao.changePassword(user)
                return DONE
            }
        } else {
            return INCORRECT_REPEAT_PASSWD
        }
    }

    int chgrp(String id, int roleID) {
        User user = dao.get(id)
        if (user.id == ROOT_ADMIN_ID) {
            return ROOT_ADMIN_CAN_NOT_CHANGE_ROLE
        } else if (RoleCache.isExists(roleID)) {
            user.roleID = roleID
            dao.changeRoleID(user)
            return DONE
        } else {
            return ROLE_IS_NOT_EXISTS
        }
    }

}
