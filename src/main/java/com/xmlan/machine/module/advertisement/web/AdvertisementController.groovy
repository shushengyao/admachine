package com.xmlan.machine.module.advertisement.web

import com.github.pagehelper.PageInfo
import com.google.common.collect.Maps
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.util.DateUtils
import com.xmlan.machine.common.util.MediaUtils
import com.xmlan.machine.common.util.SessionUtils
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.module.advertisement.entity.Advertisement
import com.xmlan.machine.module.advertisement.service.AdvertisementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
            entity.id = NEW_INSERT_ID
            entity.userID = SessionUtils.GetAdmin(request).id
        }
        return entity
    }

    @RequestMapping(value = "/detail/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    Map<String, Object> detail(@PathVariable String id) {
        Map<String, Object> data = Maps.newHashMap()
        Advertisement advertisement = AdvertisementCache.get(id.toInteger())
        data.put "ad", advertisement
        data.put "machine", AdvertisementMachineCache.getMachineNameByID(advertisement.machineID)
        return data
    }

    @RequestMapping(value = "/list/{pageNo}")
    String list(Advertisement advertisement, @PathVariable int pageNo, Model model) {
        // region 格式化查询条件
        if (advertisement.name == 'null') advertisement.name = StringUtils.EMPTY
        if (advertisement.addTime == 'null') advertisement.addTime = StringUtils.EMPTY
        if (advertisement.machineID < 1) advertisement.machineID = -2
        if (StringUtils.isNotBlank(advertisement.addTime)) {
            advertisement.addTime = "${advertisement.addTime.substring(0, 10)} 00:00:00".toString()
        }
        if (SessionUtils.GetAdmin(request).roleID != ADMIN_ROLE_ID) {
            advertisement.userID = SessionUtils.GetAdmin(request).id
        } else {
            advertisement.userID = 0
        }
        // endregion

        // region 执行查询
        List<Advertisement> list = service.findList advertisement, pageNo
        PageInfo<Advertisement> page = new PageInfo<>(list)
        model.addAttribute "page", page
        model.addAttribute "machineList", AdvertisementMachineCache.dropdownAdvertisementMachineList
        // endregion

        // region 搜索条件继承
        model.addAttribute "name", advertisement.name
        model.addAttribute "machineID", advertisement.machineID
        model.addAttribute "time", advertisement.time
        model.addAttribute "addTime", advertisement.addTime
        if (SessionUtils.GetAdmin(request).roleID != ADMIN_ROLE_ID) {
            model.addAttribute "machines", AdvertisementMachineCache.getMachineCount(SessionUtils.GetAdmin(request).id)
        } else {
            model.addAttribute "machines", PASS
        }
        // endregion

        return "advertisement/advertisementList"
    }

    @RequestMapping(value = "/form")
    String form(Advertisement advertisement, Model model) {
        model.addAttribute "advertisement", advertisement
        model.addAttribute "machineList", AdvertisementMachineCache.dropdownAdvertisementMachineList
        model.addAttribute "userList", UserCache.dropdownUserList
        "advertisement/advertisementForm"
    }

    @RequestMapping(value = "/save/{id}")
    String save(Advertisement advertisement,
                @PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, advertisement)) {
            return form(advertisement, model)
        }
        if (SessionUtils.GetAdmin(request).roleID != ADMIN_ROLE_ID) {
            advertisement.userID = SessionUtils.GetAdmin(request).id
        }
        advertisement.addTime = "${advertisement.addTime} ${DateUtils.GetTime()}"
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

    @RequestMapping(value = '/uploadMedia/{id}')
    String uploadMedia(@PathVariable String id, HttpServletRequest httpServletRequest, RedirectAttributes attributes) {
        def responseCode = service.uploadMedia(id, httpServletRequest)
        if (responseCode == DONE) {
            addMessage attributes, "上传成功"
        }
        if (responseCode == FAILURE) {
            addMessage attributes, "上传失败，文件类型错误"
        }
        "redirect:$adminPath/advertisement/list/1"
    }

    @RequestMapping('/media/{id}')
    @ResponseBody
    void media(@PathVariable String id, HttpServletResponse response) {
        Advertisement advertisement = AdvertisementCache.get(id.toInteger())
        MediaUtils.mediaTransfer advertisement.url, response
    }

}
