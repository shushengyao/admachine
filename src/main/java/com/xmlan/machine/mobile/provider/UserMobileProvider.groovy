package com.xmlan.machine.mobile.provider

import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.util.TokenUtils
import com.xmlan.machine.module.user.entity.User
import com.xmlan.machine.module.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by ayakurayuki on 2018/1/12-13:33.
 * Package: com.xmlan.machine.mobile.provider
 */
@Controller
@RequestMapping('${mobilePath}/user')
class UserMobileProvider extends BaseController {

    @Autowired
    private UserService service

    @RequestMapping(value = '/self', produces = "application/json; charset=utf-8")
    @ResponseBody
    User self(String id, String token) {
        if (TokenUtils.validateToken(token)) {
            TODO: "Validate user request."
        }
        def user = UserCache.get(id)
        user.password = ""
        return user
    }

}
