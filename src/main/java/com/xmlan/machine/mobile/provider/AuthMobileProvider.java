package com.xmlan.machine.mobile.provider;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.base.ModuleEnum;
import com.xmlan.machine.common.base.ObjectEnum;
import com.xmlan.machine.common.base.OperateEnum;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.system.service.LoginService;
import com.xmlan.machine.module.system.service.SysLogService;
import com.xmlan.machine.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 手机端 登录接口
 * <p>
 * Package: com.xmlan.machine.mobile.provider
 *
 * @author ayakurayuki
 * @date 2018/1/12-13:47
 */
@Controller
@RequestMapping("${mobilePath}")
public class AuthMobileProvider extends BaseController {

    private final LoginService loginService;
    private final SysLogService sysLogService;
    private final ThreadPoolTaskExecutor taskExecutor;

    /**
     * 构造器注入
     *
     * @param loginService  登录服务
     * @param sysLogService 系统日志
     * @param taskExecutor  线程池
     */
    @Autowired
    public AuthMobileProvider(
            LoginService loginService,
            SysLogService sysLogService,
            ThreadPoolTaskExecutor taskExecutor) {
        this.loginService = loginService;
        this.sysLogService = sysLogService;
        this.taskExecutor = taskExecutor;
    }

    /**
     * 登录
     * <p>
     * URL: /mob/auth
     * <p>
     * Method: Get/Post
     *
     * @param authname String 登录名
     * @param password String 密码
     * @return 登录结果，包括用户ID和token
     */
    @RequestMapping(value = "/auth", produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap auth(String authname, String password) {
        User user = loginService.loginForMobile(authname, password);
        // 采用客户端记录的方式，回传用户ID和Token
        if (user != null) {
            taskExecutor.execute(() ->
                    sysLogService.log(
                            ModuleEnum.User,
                            OperateEnum.Login,
                            user.getId(),
                            ObjectEnum.User,
                            "手机端登录认证"
                    )
            );
            HashMap<String, Object> map = Maps.newHashMap();
            map.put(keyID, user.getId());
            map.put(keyToken, TokenUtils.getToken(authname, password));
            return map;
        } else {
            return Maps.newHashMap();
        }
    }

}
