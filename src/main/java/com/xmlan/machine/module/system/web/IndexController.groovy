package com.xmlan.machine.module.system.web

import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.util.SessionUtils
import com.xmlan.machine.module.system.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
        "redirect:$adminPath/main"
    }

    @RequestMapping(['${adminPath}', '${adminPath}/main', '${adminPath}/login'])
    String main(HttpServletRequest request, HttpServletResponse response, Model model) {
        request.getSession().setAttribute('adminPath', adminPath)
        if (SessionUtils.GetAdmin(request) != null) {
            "system/main"
        } else {
            "system/login"
        }
    }

    @RequestMapping('${adminPath}/auth')
    String auth(String authname, String password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (service.login(request, authname, password)) {
            addMessage(redirectAttributes, "登录成功！")
            "redirect:$adminPath/main"
        } else {
            addMessage(redirectAttributes, "登录信息有误")
            "redirect:$adminPath/login"
        }
    }

    @RequestMapping('${adminPath}/logout')
    String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        SessionUtils.SetAdmin(request, null)
        addMessage(redirectAttributes, "退出成功")
        "system/login"
    }

}
