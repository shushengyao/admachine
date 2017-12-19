package com.xmlan.machine.module.system.web;

import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.module.system.service.LoginService;
import com.xmlan.machine.module.system.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ayakurayuki on 2017/12/13-15:49.
 * Package: com.xmlan.machine.module.system.web
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private LoginService service;

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "system/login";
    }

    @RequestMapping(value = "/login")
    public String login(String authname, String password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (service.login(request.getSession(), authname, password)) {
            return "system/main";
        }
        addMessage(redirectAttributes, "登录成功！");
        return "system/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        SessionUtils.SetAdmin(request.getSession(), null);
        addMessage(redirectAttributes, "退出成功");
        return "system/login";
    }

}
