package com.xmlan.machine.module.system.web;

import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by ayakurayuki on 2017/12/13-15:49.
 * Package: com.xmlan.machine.module.system.web
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private LoginService service;

    @RequestMapping({"/", "/index"})
    public String index() {
        return "redirect:" + adminPath + "/main";
    }

    @RequestMapping({"${adminPath}", "${adminPath}/main", "${adminPath}/login"})
    public String main() {
        if (SessionUtils.getAdmin(request) != null) {
            return "system/main";
        } else {
            return "system/login";
        }
    }

    @RequestMapping("${adminPath}/auth")
    public String auth(String authname, String password, RedirectAttributes redirectAttributes) {
        if (service.login(request, authname, password)) {
            addMessage(redirectAttributes, "登录成功！");
            return "redirect:" + adminPath + "/main";
        } else {
            addMessage(redirectAttributes, "登录信息有误");
            return "redirect:" + adminPath + "/login";
        }
    }

    @RequestMapping("${adminPath}/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        SessionUtils.setAdmin(request, null);
        addMessage(redirectAttributes, "退出成功");
        return "system/login";
    }

    @RequestMapping("/404")
    public String pageNotFound() {
        return "errorPage/404";
    }

    @RequestMapping("/400")
    public String dataNotMatch() {
        return "errorPage/400";
    }

    @RequestMapping("/500")
    public String systemBusy() {
        return "errorPage/500";
    }

}
