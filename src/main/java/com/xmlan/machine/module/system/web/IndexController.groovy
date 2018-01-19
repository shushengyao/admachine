package com.xmlan.machine.module.system.web

import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.util.SessionUtils
import com.xmlan.machine.module.system.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Created by ayakurayuki on 2017/12/13-15:49.
 * Package: com.xmlan.machine.module.system.web
 */
@Controller
class IndexController extends BaseController {

    @Autowired
    private LoginService service

    @RequestMapping(["/", "/index"])
    String index() {
        return "redirect:${adminPath}/main"
    }

    @RequestMapping(['${adminPath}', '${adminPath}/main', '${adminPath}/login'])
    String main() {
        if (SessionUtils.getAdmin(request) != null) {
            return "system/main"
        } else {
            return "system/login"
        }
    }

    @RequestMapping('${adminPath}/auth')
    String auth(String authname, String password, RedirectAttributes redirectAttributes) {
        if (service.login(request, authname, password)) {
            addMessage redirectAttributes, "登录成功！"
            return "redirect:${adminPath}/main"
        } else {
            addMessage redirectAttributes, "登录信息有误"
            return "redirect:${adminPath}/login"
        }
    }

    @RequestMapping('${adminPath}/logout')
    def logout(RedirectAttributes redirectAttributes) {
        SessionUtils.setAdmin request, null
        addMessage redirectAttributes, "退出成功"
        "system/login"
    }

    @RequestMapping("/404")
    def pageNotFound() {
        "errorPage/404"
    }

    @RequestMapping("/400")
    def dataNotMatch() {
        "errorPage/400"
    }

    @RequestMapping("/500")
    def systemBusy() {
        "errorPage/500"
    }

}
