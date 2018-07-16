package com.xmlan.machine.module.monitor;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
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
     * 进入监控主页
     * @return
     */
    @RequestMapping(value = "/Monitorlist")
    public String list(){
        //录像文件
        String recording = "录像文件";
        String playbackDownload = "回放下载";
        String playbackCcapture = "回放抓图";
        String playbackClip = "回放剪辑";
        String previewScreenshot ="预览抓图";
        String equipmentCapture = "设备抓图";
        File dirRecording = new File("D:\\摄像头文件\\录像文件");
        File dirPlaybackDownload = new File("D:\\摄像头文件\\回放下载");
        File dirPlaybackCcapture = new File("D:\\摄像头文件\\回放抓图");
        File dirPlaybackClip = new File("D:\\摄像头文件\\回放剪辑");
        File dirPreviewScreenshot = new File("D:\\摄像头文件\\预览抓图");
        File dirEquipmentCapture = new File("D:\\摄像头文件\\设备抓图");
        if (dirRecording.exists() ||dirPlaybackDownload.exists() || dirPlaybackCcapture.exists() || dirPlaybackClip.exists() ||
                dirPreviewScreenshot.exists() || dirEquipmentCapture.exists() ) {
            System.out.println("创建目录失败，目标目录已经存在");
        }
        if (!recording.endsWith(File.separator)) {
            recording = recording + File.separator;
        }
        //创建目录
        if (dirRecording.mkdirs()) {
            System.out.println("创建目录" + recording + "成功！");
        } else {
            System.out.println("创建目录" + recording + "失败！目标已存在！");
        }
        if (dirPlaybackDownload.mkdirs()){
            System.out.println("创建目录" + playbackDownload + "成功！");
        }else {
            System.out.println("创建目录" + playbackDownload + "失败！目标已存在！");
        }
        if (dirPlaybackCcapture.mkdirs()){
            System.out.println("创建目录" + playbackCcapture + "成功！");
        }else {
            System.out.println("创建目录" + playbackCcapture + "失败！目标已存在！");
        }
        if (dirPlaybackClip.mkdirs()){
            System.out.println("创建目录" + playbackClip + "成功！");
        }else {
            System.out.println("创建目录" + playbackDownload + "失败！目标已存在！");
        }
        if (dirPreviewScreenshot.mkdirs()){
            System.out.println("创建目录" + previewScreenshot + "成功！");
        }else {
            System.out.println("创建目录" + previewScreenshot + "失败！目标已存在！");
        }
        if (dirEquipmentCapture.mkdirs()){
            System.out.println("创建目录" + equipmentCapture + "成功！");
        }else {
            System.out.println("创建目录" + playbackDownload + "失败！目标已存在！");
        }
        return "monitor/Monitorlist";
    }

    /**
     * 打开所有监控
     * @return
     */
    @RequestMapping(value = "/index")
    @ResponseBody
    public List<AdvertisementMachine> index(@RequestParam("pageNo") int pageNo, ModelMap modelMap){
        if ("".equals(pageNo)){
            pageNo=1;
        }
        User user=(User)modelMap.get("loginUser");
        int userid = user.getId();
        List<AdvertisementMachine> machineList;
        if (userid==1){
            machineList =service.findAllMachine();
            return machineList;
        }else {
            machineList =service.adchineListByUserID(userid,pageNo);
            return machineList;
        }
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
    @RequestMapping(value = "/uikitDemo")
    public String uikitDemo (){
        return "monitor/EZUIKit_Demo_IE8";
    }

    /**
     * 传递萤石云登录需要的参数
     * @return
     */
//    @RequestMapping(value = "/uikitData")
//    public String uikitData(HttpServletRequest request){
//        request.setAttribute("cameraSequence",request.getParameter("cameraSequence"));
//        request.setAttribute("cameraVerificationCode",request.getParameter("cameraVerificationCode"));
//        return "monitor/demo/cn/demo";
//    }
    /**
     * 更新数据库accessToken字段值
     * @param accessToken
     */
    public void accessToken (String accessToken){
        service.updateAccessToken(accessToken);
    }
}
