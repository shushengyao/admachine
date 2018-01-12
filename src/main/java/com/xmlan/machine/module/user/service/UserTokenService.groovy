package com.xmlan.machine.module.user.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.module.user.dao.UserTokenDAO
import com.xmlan.machine.module.user.entity.UserToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2018/1/12-14:17.
 * Package: com.xmlan.machine.module.user.service
 */
@Service("UserTokenService")
@Transactional(readOnly = true)
class UserTokenService extends BaseService<UserToken, UserTokenDAO> {
}
