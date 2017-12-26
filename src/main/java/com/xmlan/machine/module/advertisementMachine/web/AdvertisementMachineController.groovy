package com.xmlan.machine.module.advertisementMachine.web

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.util.IDUtils
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService
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
 * Created by ayakurayuki on 2017/12/13-17:31.
 * Package: com.xmlan.machine.module.advertisementMachine.web
 */
@Controller
@RequestMapping('${adminPath}/advertisementMachine')
class AdvertisementMachineController extends BaseController {

    @Autowired
    private AdvertisementMachineService service
    @Autowired
    private UserService userService

    @ModelAttribute
    AdvertisementMachine get(@RequestParam(required = false) String id) {
        AdvertisementMachine entity = null
        if (StringUtils.isNotBlank(id)) {
            entity = service.get id
        }
        if (null == entity) {
            entity = new AdvertisementMachine()
            entity.id = NEW_INSERT_ID
        }
        return entity
    }

    @RequestMapping(value = "/detail/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    Map<String, Object> detail(@PathVariable String id) {
        Map<String, Object> data = Maps.newHashMap()
        data.put("machine", service.get(id))
        data.put("owner", userService.get(service.get(id).userID.toString()).username)
        return data
    }

    @RequestMapping(value = "/list")
    String list(AdvertisementMachine advertisementMachine, Model model) {
        model.addAttribute "list", service.findList(advertisementMachine)
        "advertisementMachine/advertisementMachineList"
    }

    @RequestMapping(value = "/form")
    String form(AdvertisementMachine advertisementMachine, Model model) {
        if (StringUtils.isBlank(advertisementMachine.codeNumber)) {
            advertisementMachine.codeNumber = IDUtils.UUID()
        }
        model.addAttribute("adMachineUsername", userService.get(advertisementMachine.userID.toString()).username)
        model.addAttribute("dropdownUserList", UserCache.getDropdownUserList())
        model.addAttribute "advertisementMachine", advertisementMachine
        "advertisementMachine/advertisementMachineForm"
    }

    @RequestMapping(value = "/save/{id}")
    String save(AdvertisementMachine advertisementMachine,
                @PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, advertisementMachine)) {
            return form(advertisementMachine, model)
        }
        if (id == NEW_INSERT_ID.toString()) {
            service.insert advertisementMachine
            addMessage redirectAttributes, "创建广告机成功"
        } else {
            advertisementMachine.id = id.toInteger()
            service.update advertisementMachine
            addMessage redirectAttributes, "修改广告机成功"
        }
        "redirect:" + adminPath + "/advertisementMachine/list"
    }

    @RequestMapping(value = "/delete")
    String delete(AdvertisementMachine advertisementMachine, RedirectAttributes redirectAttributes) {
        if (service.delete(advertisementMachine) == AdvertisementMachineService.DATABASE_DO_NOTHING) {
            addMessage redirectAttributes, "这个操作没有删除任何广告机"
        } else {
            addMessage redirectAttributes, "删除广告机成功"
        }
        "redirect:" + adminPath + "/advertisementMachine/list"
    }

}
