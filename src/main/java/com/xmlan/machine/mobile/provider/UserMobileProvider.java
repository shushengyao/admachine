package com.xmlan.machine.mobile.provider;

import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.UserCache;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 手机端 用户数据服务接口
 *
 * com.xmlan.machine.mobile.provider
 *
 * @author ayakurayuki
 * @date 2018/1/12-13:33
 */
@Controller
@RequestMapping("${mobilePath}/user")
public class UserMobileProvider extends BaseController {

    /**
     * 获取登录用户的信息
     * <p>
     * URL: /mob/user/self/{id}/{token}
     * <p>
     * Method: Get
     *
     * @param id    int 用户ID
     * @param token String token身份验证
     * @return 不包括用户密码的用户对象
     */
    @RequestMapping(value = "/self/{id}/{token}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public User self(@PathVariable("id") int id, @PathVariable("token") String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        User user = UserCache.get(id);
        user.setPassword(StringUtils.EMPTY);
        return user;
    }

}
