package com.xmlan.machine.module.user.web

import com.github.pagehelper.PageInfo
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.user.entity.User
import com.xmlan.machine.module.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Created by ayakurayuki on 2017/12/13-14:26.
 * Package: com.xmlan.machine.module.user.web
 */
@Controller
@RequestMapping('${adminPath}/user')
class UserController extends BaseController {

    @Autowired
    private UserService service

    @ModelAttribute
    User get(@RequestParam(required = false) String id) {
        User entity = null
        if (StringUtils.isNotBlank(id)) {
            entity = service.get(id)
        }
        if (null == entity) {
            entity = new User()
            entity.id = NEW_INSERT_ID
        }
        return entity
    }

    @RequestMapping(value = "/detail/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    User detail(@PathVariable String id) {
        service.get id
    }

    @RequestMapping(value = '/list/{pageNo}')
    String list(User user, @PathVariable int pageNo, Model model) {
        List<User> list = service.findList(user, pageNo)
        PageInfo<User> page = new PageInfo<>(list)
        model.addAttribute "page", page
        "user/userList"
    }

    @RequestMapping(value = '/form')
    String form(User user, Model model) {
        model.addAttribute("user", user)
        "user/userForm"
    }

    @RequestMapping(value = '/save/{id}')
    String save(User user, @PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, user)) {
            return form(user, model)
        }
        if (StringUtils.equals(id, NEW_INSERT_ID.toString())) {
            service.insert user
            addMessage redirectAttributes, "创建用户成功"
        } else {
            user.id = id.toInteger()
            service.update user
            addMessage redirectAttributes, "修改用户成功"
        }
        "redirect:$adminPath/user/list/1"
    }

    @RequestMapping(value = '/delete')
    String delete(User user, RedirectAttributes redirectAttributes) {
        if (service.delete(user) == UserService.DATABASE_DO_NOTHING) {
            addMessage redirectAttributes, "这个操作没有删除任何用户"
        } else {
            addMessage redirectAttributes, "删除用户成功"
        }
        "redirect:$adminPath/user/list/1"
    }

}
