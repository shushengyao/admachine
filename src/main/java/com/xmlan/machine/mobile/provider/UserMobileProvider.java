package com.xmlan.machine.mobile.provider;

import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.UserCache;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ayakurayuki on 2018/1/12-13:33.
 * Package: com.xmlan.machine.mobile.provider
 */
@Controller
@RequestMapping("${mobilePath}/user")
public class UserMobileProvider extends BaseController {

    @RequestMapping(value = "/self", produces = "application/json; charset=utf-8")
    @ResponseBody
    public User self(String id, String token) {
        if (TokenUtils.validateToken(token)) {
            // TODO: "Validate user request."
            System.out.println(true);
        }
        User user = UserCache.get(id);
        user.setPassword(StringUtils.EMPTY);
        return user;
    }

}
