package com.xmlan.machine.module.advertisement.web;

import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.module.advertisement.entity.Advertisement;
import com.xmlan.machine.module.advertisement.service.AdvertisementService;
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
 * Created by ayakurayuki on 2017/12/13-17:21.
 * Package: com.xmlan.machine.module.advertisement.web
 */
@Controller
@RequestMapping(value = "${adminPath}/advertisement")
public class AdvertisementController extends BaseController {

    @Autowired
    private AdvertisementService service;

    @ModelAttribute
    public Advertisement get(@RequestParam(required = false) String id) {
        Advertisement entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = service.get(id);
        }
        if (null == entity) {
            entity = new Advertisement();
            entity.setId(NEW_INSERT_ID);
        }
        return entity;
    }

    @RequestMapping(value = "/list")
    public String list(Advertisement advertisement, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Advertisement> list = service.findList(advertisement);
        model.addAttribute("list", list);
        return "advertisement/advertisementList";
    }

    @RequestMapping(value = "/form")
    public String form(Advertisement advertisement, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("advertisement", advertisement);
        return "advertisement/advertisementForm";
    }

    @RequestMapping(value = "/save")
    public String save(Advertisement advertisement, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (!beanValidator(model, advertisement)) {
            return form(advertisement, request, response, model);
        }
        if (advertisement.getId() == NEW_INSERT_ID) {
            service.insert(advertisement);
            addMessage(model, "创建广告成功");
        } else {
            service.update(advertisement);
            addMessage(model, "修改广告成功");
        }
        return "redirect:" + adminPath + "/advertisement/list";
    }

    @RequestMapping(value = "/delete")
    public String delete(Advertisement advertisement, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (service.delete(advertisement) == AdvertisementService.DATABASE_DO_NOTHING) {
            addMessage(model, "这个操作没有删除任何广告");
        } else {
            addMessage(model, "删除广告成功");
        }
        return "redirect:" + adminPath + "/advertisement/list";
    }

}
