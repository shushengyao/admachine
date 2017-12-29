package com.xmlan.machine.module.user.web

import com.github.pagehelper.PageInfo
import com.google.common.collect.Maps
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.RoleCache
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.role.service.RoleService
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

import javax.servlet.http.HttpServletRequest

/**
 * Created by ayakurayuki on 2017/12/13-14:26.
 * Package: com.xmlan.machine.module.user.web
 */
@Controller
@RequestMapping('${adminPath}/user')
class UserController extends BaseController {

    @Autowired
    private UserService service
    @Autowired
    private RoleService roleService

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
    Map<String, Object> detail(@PathVariable String id) {
        Map<String, Object> data = Maps.newHashMap()
        data.put("user", UserCache.get(id))
        data.put("roleName", RoleCache.get(UserCache.get(id).roleID.toString()).name)
        return data
    }

    @RequestMapping(value = '/list/{pageNo}')
    String list(User user, @PathVariable int pageNo, Model model) {
        if (StringUtils.isNotBlank(user.addTime)) {
            user.addTime = user.addTime.toString().substring 0, 10
        }
        if (user.roleID < 1) {
            user.roleID = -2
        }

        List<User> list = service.findList(user, pageNo)
        PageInfo<User> page = new PageInfo<>(list)
        model.addAttribute "page", page

        model.addAttribute "dropdownRoleList", RoleCache.roleList
        model.addAttribute "username", user.username
        model.addAttribute "authname", user.authname
        model.addAttribute "addTime", user.addTime
        model.addAttribute "roleID", user.roleID.toString()
        model.addAttribute "roleName", RoleCache.get(user.roleID.toString())?.name
        "user/userList"
    }

    @RequestMapping(value = '/form')
    String form(User user, Model model) {
        model.addAttribute "user", user
        model.addAttribute "dropdownRoleList", RoleCache.roleList
        model.addAttribute "roleName", RoleCache.get(user.roleID.toString())?.name
        "user/userForm"
    }

    @RequestMapping(value = '/save/{id}')
    String save(User user,
                @PathVariable String id, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, user)) {
            return form(user, model)
        }
        if (!StringUtils.equals(user.password, request.getParameter("confirmPassword"))) {
            addMessage redirectAttributes, "确认密码与登录密码不匹配"
            return form(user, model)
        }
        if (!(user.phone ==~ /((86)|(\+86))?(\-)?1[3|4|5|7|8][0-9]\d{8}/)) {
            addMessage redirectAttributes, "手机号码格式不正确"
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
        int responseCode = service.delete(user)
        if (responseCode == DATABASE_DO_NOTHING) {
            addMessage redirectAttributes, "这个操作没有删除任何用户"
        } else if (responseCode == UserService.USER_HAVE_SOME_MACHINES) {
            addMessage redirectAttributes, "这个用户拥有广告机，不能删除"
        } else {
            addMessage redirectAttributes, "删除用户成功"
        }
        "redirect:$adminPath/user/list/1"
    }

}
