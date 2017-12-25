package com.xmlan.machine.module.advertisementMachine.web

import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.util.IDUtils
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.List

/**
 * Created by ayakurayuki on 2017/12/13-17:31.
 * Package: com.xmlan.machine.module.advertisementMachine.web
 */
@Controller
@RequestMapping(value = '${adminPath}/advertisementMachine')
class AdvertisementMachineController extends BaseController {

    @Autowired
    private AdvertisementMachineService service

    @ModelAttribute
    AdvertisementMachine get(@RequestParam(required = false) String id) {
        AdvertisementMachine entity = null
        if (StringUtils.isNotBlank(id)) {
            entity = service.get(id)
        }
        if (null == entity) {
            entity = new AdvertisementMachine()
            entity.setId(NEW_INSERT_ID)
        }
        return entity
    }

    @RequestMapping(value = "/list")
    String list(AdvertisementMachine advertisementMachine, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<AdvertisementMachine> list = service.findList(advertisementMachine)
        model.addAttribute("list", list)
        return "advertisementMachine/advertisementMachineList"
    }

    @RequestMapping(value = "/form")
    String form(AdvertisementMachine advertisementMachine, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (StringUtils.isBlank(advertisementMachine.codeNumber)) {
            advertisementMachine.codeNumber = IDUtils.UUID()
        }
        model.addAttribute("advertisementMachine", advertisementMachine)
        "advertisementMachine/advertisementMachineForm"
    }

    @RequestMapping(value = "/save")
    String save(AdvertisementMachine advertisementMachine, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (!beanValidator(model, advertisementMachine)) {
            return form(advertisementMachine, request, response, model)
        }
        if (advertisementMachine.id == NEW_INSERT_ID) {
            service.insert advertisementMachine
            addMessage model, "创建广告机成功"
        } else {
            service.update advertisementMachine
            addMessage model, "修改广告机成功"
        }
        "redirect:" + adminPath + "/advertisementMachine/list"
    }

    @RequestMapping(value = "/delete")
    String delete(AdvertisementMachine advertisementMachine, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (service.delete(advertisementMachine) == AdvertisementMachineService.DATABASE_DO_NOTHING) {
            addMessage(model, "这个操作没有删除任何广告机")
        } else {
            addMessage(model, "删除广告机成功")
        }
        "redirect:" + adminPath + "/advertisementMachine/list"
    }

}
