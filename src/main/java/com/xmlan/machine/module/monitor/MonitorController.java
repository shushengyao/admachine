package com.xmlan.machine.module.monitor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.task.HttpTools;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.user.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-05-11 14:54
 * 监控控制类
 **/
@Controller
@RequestMapping("${adminPath}/monitor")
public class MonitorController extends BaseController {
    @Autowired
    private AdvertisementMachineService service;

    /**
     * 打开所有监控
     * @return
     */
    @RequestMapping(value = "/list/{pageNo}")
    public String  list(AdvertisementMachine advertisementMachine,@PathVariable("pageNo") int pageNo, Model model, ModelMap modelMap){
        if ("".equals(pageNo)){
            pageNo=1;
        }
        User user=(User)modelMap.get("loginUser");
        int userID = user.getId();
        advertisementMachine.setUserID(userID);
        List<AdvertisementMachine> machineList;
        if (userID==1 || userID == 10){
            machineList =service.findAll(pageNo);
        }else if (user.getRoleID() ==1){
            machineList =service.atmosphereListByUserID(userID,pageNo);
        }else {
            machineList =service.generalFindList(advertisementMachine,pageNo);
        }
        PageInfo<AdvertisementMachine> page = new PageInfo<>(machineList);
        model.addAttribute( "page", page);
        return "monitor/Monitorlist";
    }

    /**
     * 进入监控主页
     * @return
     */
    @RequestMapping(value = "/Monitorlist")
    public String list(){
        //录像文件
//        String recording = "录像文件";
//        String playbackDownload = "回放下载";
//        String playbackCcapture = "回放抓图";
//        String playbackClip = "回放剪辑";
//        String previewScreenshot ="预览抓图";
//        String equipmentCapture = "设备抓图";
//        File dirRecording = new File("D:\\摄像头文件\\录像文件");
//        File dirPlaybackDownload = new File("D:\\摄像头文件\\回放下载");
//        File dirPlaybackCcapture = new File("D:\\摄像头文件\\回放抓图");
//        File dirPlaybackClip = new File("D:\\摄像头文件\\回放剪辑");
//        File dirPreviewScreenshot = new File("D:\\摄像头文件\\预览抓图");
//        File dirEquipmentCapture = new File("D:\\摄像头文件\\设备抓图");
//        if (dirRecording.exists() ||dirPlaybackDownload.exists() || dirPlaybackCcapture.exists() || dirPlaybackClip.exists() ||
//                dirPreviewScreenshot.exists() || dirEquipmentCapture.exists() ) {
//            System.out.println("创建目录失败，目标目录已经存在");
//        }
//        if (!recording.endsWith(File.separator)) {
//            recording = recording + File.separator;
//        }
//        //创建目录
//        if (dirRecording.mkdirs()) {
//            System.out.println("创建目录" + recording + "成功！");
//        } else {
//            System.out.println("创建目录" + recording + "失败！目标已存在！");
//        }
//        if (dirPlaybackDownload.mkdirs()){
//            System.out.println("创建目录" + playbackDownload + "成功！");
//        }else {
//            System.out.println("创建目录" + playbackDownload + "失败！目标已存在！");
//        }
//        if (dirPlaybackCcapture.mkdirs()){
//            System.out.println("创建目录" + playbackCcapture + "成功！");
//        }else {
//            System.out.println("创建目录" + playbackCcapture + "失败！目标已存在！");
//        }
//        if (dirPlaybackClip.mkdirs()){
//            System.out.println("创建目录" + playbackClip + "成功！");
//        }else {
//            System.out.println("创建目录" + playbackDownload + "失败！目标已存在！");
//        }
//        if (dirPreviewScreenshot.mkdirs()){
//            System.out.println("创建目录" + previewScreenshot + "成功！");
//        }else {
//            System.out.println("创建目录" + previewScreenshot + "失败！目标已存在！");
//        }
//        if (dirEquipmentCapture.mkdirs()){
//            System.out.println("创建目录" + equipmentCapture + "成功！");
//        }else {
//            System.out.println("创建目录" + playbackDownload + "失败！目标已存在！");
//        }
        return "monitor/Monitorlist";
    }


    /**
     * 调用萤石云demo主页
     * @return
     */
    @RequestMapping(value = "/demo")
    public String demo (){
        return "monitor/demo/cn/demo";
    }

    /**
     * 调用摄像头demo主页
     * @return
     */
    @RequestMapping(value = "/uikit")
    public String uikit (){
        return "monitor/EZUIKit_Demo_IE8";
    }

    /**
     * 更新accesstoken
     * @return
     */
    @RequestMapping(value = "/update")
    public String update(){
        Map<String, String> parms =new HashMap<>();
        parms.put("appKey","51a534ebadf54c31a0848dc575dfa206");
        parms.put("appSecret","8c32c67a73c87b9e461b2e3bdf58967a");
        String post = HttpTools.httpRequestToString("https://open.ys7.com/api/lapp/token/get","post",parms);
        JSONObject jsonObj= JSON.parseObject(post);
        String data = jsonObj.getString("data");
        JSONObject jsondata= JSON.parseObject(data);
        String accessToken= jsondata.getString("accessToken");
        service.updateAccessToken(accessToken);
        System.out.print("更新accessToken");
        return "monitor/Monitorlist";
    }

    /**
     * 更新accesstoken
     */

//    public static void main(String[] args){
//        String post ="{\"data\":{\"accessToken\":\"at.1rwk0g8h0p5meyqqaxuaigx668akbgfd-2kpgl7456q-0xxivp3-flvlz1tnu\",\"expireTime\":1537325846082},\"code\":\"200\",\"msg\":\"操作成功!\"}";
////        System.out.print(post);
//        JSONObject jsonObj= JSON.parseObject(post);
//        String data = jsonObj.getString("data");
//        JSONObject jsondata= JSON.parseObject(data);
//        String value= jsondata.getString("accessToken");
////        String token = value.toString();
//        System.out.print(value);
////        System.out.print(post);
//    }

}
