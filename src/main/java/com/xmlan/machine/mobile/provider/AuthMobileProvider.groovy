package com.xmlan.machine.mobile.provider

import com.google.common.collect.Maps
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.util.TokenUtils
import com.xmlan.machine.module.system.service.LoginService
import com.xmlan.machine.module.user.entity.User

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by ayakurayuki on 2018/1/12-13:47.
 * Package: com.xmlan.machine.mobile.provider
 */
@Controller
@RequestMapping('${mobilePath}')
class AuthMobileProvider extends BaseController {

    @Autowired
    private LoginService loginService

    @RequestMapping(value = '/auth', produces = "application/json; charset=utf-8")
    @ResponseBody
    HashMap auth(String authname, String password) {
        User user = loginService.loginForMobile(authname, password)
        // 采用客户端记录的方式，回传用户ID和Token
        if (user != null) {
            def map = Maps.newHashMap()
            map['id'] = user.id
            map['token'] = TokenUtils.getToken(authname, password)
            return map
        } else {
            return Maps.newHashMap()
        }
    }

}
