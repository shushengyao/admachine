package com.xmlan.machine.module.role.web

import com.github.pagehelper.PageInfo
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.role.entity.Role
import com.xmlan.machine.module.role.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Created by ayakurayuki on 2017/12/13-11:09.
 * Package: com.xmlan.machine.module.role.web
 */
@Controller
@RequestMapping('${adminPath}/role')
class RoleController extends BaseController {

    @Autowired
    private RoleService service

    @ModelAttribute
    Role get(@RequestParam(required = false) String id) {
        Role entity = null
        if (StringUtils.isNotBlank(id)) {
            entity = service.get(id)
        }
        if (null == entity) {
            entity = new Role()
            entity.id = NEW_INSERT_ID
        }
        return entity
    }

    @RequestMapping(value = "/detail/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    Role detail(@PathVariable String id) {
        service.get(id)
    }

    @RequestMapping(value = "/list/{pageNo}")
    String list(Role role, @PathVariable int pageNo, Model model) {
        // region 格式化查询条件
        // endregion

        // region 执行查询
        List<Role> list = service.findList role, pageNo
        PageInfo<Role> page = new PageInfo<>(list)
        model.addAttribute "page", page
        // endregion

        // region 搜索条件继承
        // endregion

        "role/roleList"
    }

    @RequestMapping(value = "/form")
    String form(Role role, Model model) {
        model.addAttribute "role", role
        "role/roleForm"
    }

    @RequestMapping(value = "/save/{id}")
    String save(Role role, @PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, role)) {
            return form(role, model)
        }
        if (StringUtils.equals(id, NEW_INSERT_ID.toString())) {
            service.insert role
            addMessage redirectAttributes, "创建角色成功"
        } else {
            role.id = id.toInteger()
            service.update role
            addMessage redirectAttributes, "修改角色成功"
        }
        "redirect:$adminPath/role/list/1"
    }

    @RequestMapping(value = "/delete")
    String delete(Role role, RedirectAttributes redirectAttributes) {
        if (service.delete(role) == RoleService.DATABASE_DO_NOTHING) {
            addMessage redirectAttributes, "什么都没有删除"
        } else {
            addMessage redirectAttributes, "删除角色成功"
        }
        "redirect:$adminPath/role/list/1"
    }

}
