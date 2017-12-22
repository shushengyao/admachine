package com.xmlan.machine.module.user.web

import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.user.entity.User
import com.xmlan.machine.module.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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

    @RequestMapping(value = '/list')
    String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<User> list = service.findList(user)
        model.addAttribute("list", list)
        return "user/userList"
    }

    @RequestMapping(value = '/form')
    String form(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("user", user)
        return "user/userForm"
    }

    @RequestMapping(value = '/save')
    String save(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (!beanValidator(model, user)) {
            return form(user, request, response, model)
        }
        if (user.getId() == NEW_INSERT_ID) {
            service.insert(user)
            addMessage(model, "创建用户成功")
        } else {
            service.update(user)
            addMessage(model, "修改用户成功")
        }
        return "redirect:$adminPath/user/list"
    }

    @RequestMapping(value = '/delete')
    String delete(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (service.delete(user) == UserService.DATABASE_DO_NOTHING) {
            addMessage(model, "这个操作没有删除任何用户")
        } else {
            addMessage(model, "删除用户成功")
        }
        return "redirect:$adminPath/user/list"
    }

}
