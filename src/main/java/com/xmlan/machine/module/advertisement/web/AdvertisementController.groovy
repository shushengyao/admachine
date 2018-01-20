package com.xmlan.machine.module.advertisement.web

import cn.jiguang.common.ClientConfig
import cn.jiguang.common.resp.APIRequestException
import cn.jpush.api.JPushClient
import com.github.pagehelper.PageInfo
import com.google.common.collect.Maps
import com.xmlan.machine.common.base.BaseController
import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.config.Global
import com.xmlan.machine.common.util.*
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

    /**
     * 页面属性
     * @param id 广告ID
     * @return 获取到的实体类
     */
    @ModelAttribute
    Advertisement get(@RequestParam(required = false) String id) {
        Advertisement entity = null
        if (StringUtils.isNotBlank(id)) {
            entity = service.get(id)
        }
        if (null == entity) {
            entity = new Advertisement()
            entity.id = NEW_INSERT_ID
            entity.userID = SessionUtils.getAdmin(request).id
        }
        return entity
    }

    /**
     * 广告详细信息
     * @param id 广告ID
     * @return JSON封装的广告对象
     */
    @RequestMapping(value = "/detail/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    Map<String, Object> detail(@PathVariable String id) {
        Map<String, Object> data = Maps.newHashMap()
        Advertisement advertisement = AdvertisementCache.get(id.toInteger())
        data["ad"] = advertisement
        data["machine"] = AdvertisementMachineCache.getMachineNameByID(advertisement.machineID)
        return data
    }

    /**
     * 列表和列表查询
     * @param advertisement 查询条件实体
     * @param pageNo 当前页面
     * @param model 传参对象
     * @return
     */
    @RequestMapping(value = "/list/{pageNo}")
    String list(Advertisement advertisement, @PathVariable int pageNo, Model model) {
        if (advertisement.name == 'null') advertisement.name = StringUtils.EMPTY
        if (advertisement.addTime == 'null') advertisement.addTime = StringUtils.EMPTY
        if (advertisement.machineID < 1) advertisement.machineID = NEW_INSERT_ID
        if (StringUtils.isNotBlank(advertisement.addTime)) {
            advertisement.addTime = "${advertisement.addTime.substring(0, 10)} 00:00:00".toString()
        }
        if (SessionUtils.getAdmin(request).roleID != ADMIN_ROLE_ID) {
            // 角色不是管理员则限定仅查询属于自己的广告
            advertisement.userID = SessionUtils.getAdmin(request).id
        } else {
            advertisement.userID = 0
        }
        // 分页
        List<Advertisement> list = service.findList advertisement, pageNo
        PageInfo<Advertisement> page = new PageInfo<>(list)
        model.addAttribute "page", page
        model.addAttribute "machineList", AdvertisementMachineCache.dropdownAdvertisementMachineList
        model.addAttribute "name", advertisement.name
        model.addAttribute "machineID", advertisement.machineID
        model.addAttribute "time", advertisement.time
        model.addAttribute "addTime", advertisement.addTime
        if (SessionUtils.getAdmin(request).roleID != ADMIN_ROLE_ID) {
            // 添加广告判断，普通用户没有广告机的时候不能添加广告
            model.addAttribute "machines", AdvertisementMachineCache.getMachineCount(SessionUtils.getAdmin(request).id)
        } else {
            // 管理员则通过总的广告机数量判断可否添加广告
            model.addAttribute "machines", AdvertisementMachineCache.machineCount
        }

        return "advertisement/advertisementList"
    }

    /**
     * 表单编辑视图跳转
     * @param advertisement 广告对象
     * @param model 传参对象
     * @return
     */
    @RequestMapping(value = "/form")
    String form(Advertisement advertisement, Model model) {
        model.addAttribute "advertisement", advertisement
        model.addAttribute "machineList", AdvertisementMachineCache.dropdownAdvertisementMachineList
        model.addAttribute "userList", UserCache.dropdownUserList
        "advertisement/advertisementForm"
    }

    /**
     * 表单保存
     * @param advertisement 广告对象
     * @param id 广告ID
     * @param model 传参对象
     * @param redirectAttributes 跳转属性
     * @return
     */
    @RequestMapping(value = "/save/{id}")
    String save(Advertisement advertisement, @PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, advertisement)) {
            return form(advertisement, model)
        }
        if (SessionUtils.getAdmin(request).roleID != ADMIN_ROLE_ID) {
            advertisement.userID = SessionUtils.getAdmin(request).id
        }
        if (id == NEW_INSERT_ID) {
            advertisement.addTime = DateUtils.GetDateTime()
            service.insert advertisement
            // 推送
            push(advertisement.machineID, advertisement.id, "New advertisement.")
            addMessage redirectAttributes, "创建广告成功"
        } else {
            advertisement.id = id
            service.update advertisement
            // 推送
            push(advertisement.machineID, advertisement.id, "An advertisement updated.")
            addMessage redirectAttributes, "修改广告成功"
        }
        "redirect:$adminPath/advertisement/list/1"
    }

    /**
     * 删除广告
     * @param advertisement 广告对象
     * @param attributes 跳转属性
     * @return
     */
    @RequestMapping(value = "/delete")
    String delete(Advertisement advertisement, RedirectAttributes attributes) {
        if (service.delete(advertisement) == DATABASE_DO_NOTHING) {
            addMessage attributes, "这个操作没有删除任何广告"
        } else {
            addMessage attributes, "删除广告成功"
        }
        "redirect:$adminPath/advertisement/list/1"
    }

    /**
     * 上传媒体
     * @param id 广告ID
     * @param request 上传请求
     * @param attributes 跳转属性
     * @return
     */
    @RequestMapping(value = '/uploadMedia/{id}')
    String uploadMedia(@PathVariable int id, HttpServletRequest request, RedirectAttributes attributes) {
        def responseCode = service.uploadMedia(String.valueOf(id), request)
        if (responseCode == DONE) {
            def ad = AdvertisementCache.get(id)
            // 推送
            push(ad.machineID, id, "New advertisement media.")
            addMessage attributes, "上传成功"
        }
        if (responseCode == FAILURE) {
            addMessage attributes, "上传失败，文件类型错误"
        }
        "redirect:$adminPath/advertisement/list/1"
    }

    /**
     * 媒体请求
     * @param id 广告ID
     * @param response 输出流
     */
    @RequestMapping('/media/{id}')
    @ResponseBody
    void media(@PathVariable int id, HttpServletResponse response) {
        Advertisement advertisement = AdvertisementCache.get(id)
        MediaUtils.mediaTransfer advertisement.url, response
    }

    /**
     * 推送方法
     */
    private def push = { int machineID, int advertisementID, String message ->
        logger.trace "New push task with machine-No.${machineID}, ad-No.${advertisementID}, message-${message}"
        def machine = AdvertisementMachineCache.get(machineID)
        HashMap<String, Integer> map = Maps.newHashMap()
        map['advertisementID'] = advertisementID
        map['type'] = TYPE_MEDIA_UPDATE
        def pushClient = new JPushClient(Global.masterSecret, Global.appKey, null, ClientConfig.instance)
        def payload = PushUtils.buildPayload(machine.toString(), message, map)
        try {
            def result = pushClient.sendPush(payload)
            logger.trace result
        } catch (APIRequestException e) {
            logger.error "API exception with: ${e.message}"
        }
    }

}
