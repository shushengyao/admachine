package com.xmlan.machine.module.advertisementMachine.web

import com.github.pagehelper.PageInfo
import com.google.common.collect.Maps
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.util.DateUtils
import com.xmlan.machine.common.util.SessionUtils
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.common.util.TokenUtils
import com.xmlan.machine.module.advertisement.service.AdvertisementService
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService
import com.xmlan.machine.module.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

import javax.servlet.http.HttpServletRequest

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
    @Autowired
    private AdvertisementService advertisementService

    @ModelAttribute
    AdvertisementMachine get(@RequestParam(required = false) String id) {
        AdvertisementMachine entity = null
        if (StringUtils.isNotBlank(id)) {
            entity = service.get id
        }
        if (null == entity) {
            entity = new AdvertisementMachine()
            entity.id = NEW_INSERT_ID
            entity.addTime = StringUtils.SPACE
            entity.light = 0
            entity.charge = 0
            entity.checked = 0
        }
        return entity
    }

    @RequestMapping(value = "/detail/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    Map<String, Object> detail(@PathVariable String id) {
        Map<String, Object> data = Maps.newHashMap()
        def machine = service.get(id)
        data["machine"] = machine
        data["owner"] = userService.get(service.get(id).userID.toString()).username
        data["ads"] = AdvertisementCache.getAdvertisementCountByMachineID(id.toInteger())
        data["status"] = "${machine.light == 1 ? '灯亮️' : '灯灭'}/${machine.charge == 1 ? '充电' : '闲置'}".toString()
        return data
    }

    @RequestMapping(value = "/list/{pageNo}")
    String list(AdvertisementMachine advertisementMachine, @PathVariable int pageNo, Model model) {
        // region 格式化查询条件
        if (StringUtils.isNotBlank(advertisementMachine.addTime) && advertisementMachine.addTime != StringUtils.SPACE) {
            advertisementMachine.addTime = "${advertisementMachine.addTime.substring(0, 10)} 00:00:00".toString()
        }
        if (SessionUtils.getAdmin(request).roleID != ADMIN_ROLE_ID) {
            advertisementMachine.userID = SessionUtils.getAdmin(request).id
        }
        if (advertisementMachine.addTime == StringUtils.SPACE) {
            advertisementMachine.addTime = ''
        }
        // endregion

        // region 执行查询
        List<AdvertisementMachine> list = service.findList advertisementMachine, pageNo
        PageInfo<AdvertisementMachine> page = new PageInfo<>(list)
        model.addAttribute "page", page
        model.addAttribute "adCount", advertisementService.getAdvertisementCount(list)
        // endregion

        // region 搜索条件继承
        model.addAttribute "name", advertisementMachine.name
        model.addAttribute "codeNumber", advertisementMachine.codeNumber
        model.addAttribute "addTime", advertisementMachine.addTime
        model.addAttribute "deleteToken", TokenUtils.getFormToken(request, "deleteToken")
        // endregion

        "advertisementMachine/advertisementMachineList"
    }

    @RequestMapping(value = "/form")
    String form(AdvertisementMachine advertisementMachine, Model model) {
        if (StringUtils.isBlank(advertisementMachine.codeNumber)) {
            advertisementMachine.codeNumber = ''
        }
        model.addAttribute "userList", UserCache.dropdownUserList
        model.addAttribute "machine", advertisementMachine
        model.addAttribute "token", TokenUtils.getFormToken(request)
        "advertisementMachine/advertisementMachineForm"
    }

    @RequestMapping(value = "/save/{id}")
    String save(AdvertisementMachine advertisementMachine,
                @PathVariable String id, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!TokenUtils.validateFormToken(request, request.getParameter("token"))) {
            addMessage redirectAttributes, "本次提交的表单验证失败"
            return "redirect:$adminPath/advertisementMachine/list/1"
        }
        if (!beanValidator(model, advertisementMachine)) {
            return form(advertisementMachine, model)
        }

        if (!(advertisementMachine.codeNumber ==~ /([A-Fa-f0-9]{2}[:-]){5}[A-Fa-f0-9]{2}/)) {
            addMessage redirectAttributes, "机器识别码格式不正确，机器识别码是广告机的MAC地址！"
            return form(advertisementMachine, model)
        } else {
            advertisementMachine.codeNumber = advertisementMachine.codeNumber.replaceAll('-', ':')
        }

        if (StringUtils.isBlank(advertisementMachine.longitude)) {
            advertisementMachine.longitude = "0.0"
        }
        if (StringUtils.isBlank(advertisementMachine.latitude)) {
            advertisementMachine.latitude = "0.0"
        }

        if (StringUtils.equals(id, NEW_INSERT_ID.toString())) {
            advertisementMachine.addTime = DateUtils.dateTime
            service.insert advertisementMachine
            addMessage redirectAttributes, "创建广告机成功"
        } else {
            advertisementMachine.id = id.toInteger()
            service.update advertisementMachine
            addMessage redirectAttributes, "修改广告机成功"
        }
        "redirect:$adminPath/advertisementMachine/list/1"
    }

    @RequestMapping(value = "/delete")
    String delete(AdvertisementMachine advertisementMachine, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (!TokenUtils.validateFormToken(request, "deleteToken", request.getParameter("deleteToken"))) {
            addMessage redirectAttributes, "本次提交的表单验证失败"
            return "redirect:$adminPath/advertisementMachine/list/1"
        }
        if (service.delete(advertisementMachine) == DATABASE_DO_NOTHING) {
            addMessage redirectAttributes, "这个操作没有删除任何广告机"
        } else {
            addMessage redirectAttributes, "删除广告机成功"
        }
        "redirect:$adminPath/advertisementMachine/list/1"
    }

}
