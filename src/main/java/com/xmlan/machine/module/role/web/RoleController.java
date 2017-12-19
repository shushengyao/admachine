package com.xmlan.machine.module.role.web;

import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.module.role.entity.Role;
import com.xmlan.machine.module.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ayakurayuki on 2017/12/13-11:09.
 * Package: com.xmlan.machine.module.role.web
 */
@Controller
@RequestMapping(value = "${adminPath}/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService service;

    @ModelAttribute
    public Role get(@RequestParam(required = false) String id) {
        Role entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = service.get(id);
        }
        if (null == entity) {
            entity = new Role();
            entity.setId(NEW_INSERT_ID);
        }
        return entity;
    }

    @RequestMapping(value = "/list")
    public String list(Role role, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Role> list = service.findList(role);
        model.addAttribute("list", list);
        return "role/roleList";
    }

    @RequestMapping(value = "/form")
    public String form(Role role, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("role", role);
        return "role/roleForm";
    }

    @RequestMapping(value = "/save")
    public String save(Role role, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (!beanValidator(model, role)) {
            return form(role, request, response, model);
        }
        if (role.getId() == NEW_INSERT_ID) {
            service.insert(role);
            addMessage(model, "创建角色成功");
        } else {
            service.update(role);
            addMessage(model, "修改角色成功");
        }
        return "redirect:" + adminPath + "/role/list";
    }

    @RequestMapping(value = "/delete")
    public String delete(Role role, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (service.delete(role) == RoleService.DATABASE_DO_NOTHING) {
            addMessage(model, "什么都没有删除");
        } else {
            addMessage(model, "删除角色成功");
        }
        return "redirect:" + adminPath + "/role/list";
    }

}
