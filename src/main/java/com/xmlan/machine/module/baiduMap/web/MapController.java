package com.xmlan.machine.module.baiduMap.web;


import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.user.entity.User;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-05-29 13:37
 * 地图显示
 **/
@Controller
@RequestMapping("${adminPath}/baiduMap")
public class MapController extends BaseController {
    @Autowired
    private AdvertisementMachineService service;

    /**
     *  打开初始化地图
     * @return
     */
    @RequestMapping(value = "/map")
    String map(){
        return "baiduMap/baiduMap";
    }

    /**
     * 显示所有设备
     * @return
     */
    @RequestMapping(value = "/index", produces = "application/json; charset=utf-8")
    @ResponseBody
    List<AdvertisementMachine> index(ModelMap modelMap){
        List<AdvertisementMachine> list;
        Object object = modelMap.get("loginUser");
        User user = (User) object;
        int userid = user.getId();
        if (userid==1 || userid == 10){
            list= service.findAllMachine();
            return list;
        }else {
            list = service.adchineListByUserID(userid);
            return list;
        }
    }
    public static void main(String[] args){
//        String res="{\"data\":{\"access_token\":\"5a7040ccf66bafd06acd39b6f61c19230eaba426755509646d6da23ddd9fb206\",\"expires_second\":36000},\"rlt_code\":\"HH0000\",\"rlt_msg\":\"成功\"}";
//        JSONObject jsonObject= JSON.parseObject(res);
//        String data = jsonObject.getString("data");
//        JSONObject jsondata= JSON.parseObject(data);
//        String token = jsondata.getString("access_token");
    }
}
