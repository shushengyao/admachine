package com.xmlan.machine.module.advertisement.web

import com.github.pagehelper.PageInfo
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.advertisement.entity.Advertisement
import com.xmlan.machine.module.advertisement.service.AdvertisementService
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
 * Created by ayakurayuki on 2017/12/13-17:21.
 * Package: com.xmlan.machine.module.advertisement.web
 */
@Controller
@RequestMapping('${adminPath}/advertisement')
class AdvertisementController extends BaseController {

    @Autowired
    private AdvertisementService service

    @ModelAttribute
    Advertisement get(@RequestParam(required = false) String id) {
        Advertisement entity = null
        if (StringUtils.isNotBlank(id)) {
            entity = service.get(id)
        }
        if (null == entity) {
            entity = new Advertisement()
            entity.setId(NEW_INSERT_ID)
        }
        return entity
    }

    @RequestMapping(value = "/detail/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    String detail(@PathVariable String id) {
        service.get(id)
    }

    @RequestMapping(value = "/list/{pageNo}")
    String list(Advertisement advertisement, @PathVariable int pageNo, Model model) {
        // region 格式化查询条件
        if (advertisement.machineID < 1) {
            advertisement.machineID = -2
        }
        // endregion

        // region 执行查询
        List<Advertisement> list = service.findList advertisement, pageNo
        PageInfo<Advertisement> page = new PageInfo<>(list)
        model.addAttribute "page", page
        model.addAttribute "dropdownList", AdvertisementMachineCache.dropdownAdvertisementMachineList
        // endregion

        // region 搜索条件继承
        model.addAttribute "name", advertisement.name
        model.addAttribute "machineID", advertisement.machineID
        model.addAttribute "machineName", AdvertisementMachineCache.getMachineNameByID(advertisement.machineID)
        model.addAttribute "time", advertisement.time
        model.addAttribute "addTime", advertisement.addTime
        // endregion

        return "advertisement/advertisementList"
    }

    @RequestMapping(value = "/form")
    String form(Advertisement advertisement, Model model) {
        model.addAttribute "advertisement", advertisement
        "advertisement/advertisementForm"
    }

    @RequestMapping(value = "/save/{id}")
    String save(Advertisement advertisement,
                @PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, advertisement)) {
            return form(advertisement, model)
        }
        if (StringUtils.equals(id, NEW_INSERT_ID.toString())) {
            service.insert advertisement
            addMessage redirectAttributes, "创建广告成功"
        } else {
            advertisement.id = id.toInteger()
            service.update advertisement
            addMessage redirectAttributes, "修改广告成功"
        }
        "redirect:$adminPath/advertisement/list/1"
    }

    @RequestMapping(value = "/delete")
    String delete(Advertisement advertisement, RedirectAttributes redirectAttributes) {
        if (service.delete(advertisement) == DATABASE_DO_NOTHING) {
            addMessage redirectAttributes, "这个操作没有删除任何广告"
        } else {
            addMessage redirectAttributes, "删除广告成功"
        }
        "redirect:$adminPath/advertisement/list/1"
    }

}
