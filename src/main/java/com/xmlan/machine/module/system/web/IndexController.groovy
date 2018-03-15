package com.xmlan.machine.module.system.web

import com.github.pagehelper.PageInfo
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.util.SessionUtils
import com.xmlan.machine.module.system.entity.SysLog
import com.xmlan.machine.module.system.service.LoginService
import com.xmlan.machine.module.system.service.SysLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Created by ayakurayuki on 2017/12/13-15:49.
 * Package: com.xmlan.machine.module.system.web
 */
@Controller
@ControllerAdvice
class IndexController extends BaseController {

    @Autowired
    private LoginService service
    @Autowired
    private SysLogService sysLogService

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

    @RequestMapping('${adminPath}/log/{pageNo}')
    def log(SysLog sysLog, @PathVariable int pageNo, Model model) {
        def list = sysLogService.findList(sysLog, pageNo)
        def page = new PageInfo<SysLog>(list)
        model.addAttribute "page", page
        "system/log"
    }

    @RequestMapping("/404")
    def pageNotFound() {
        "errorPage/404"
    }

    @RequestMapping("/400")
    def dataNotMatch() {
        "errorPage/400"
    }

    @ExceptionHandler
    @RequestMapping("/500")
    def systemBusy(Exception e, Model model) {
        model.addAttribute "exception", e.message
        "errorPage/500"
    }

}
