package com.xmlan.machine.module.xixun.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.UserCache;
import com.xmlan.machine.common.util.FileUtils;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.common.util.UploadUtils;
import com.xmlan.machine.module.led_machine.entity.Led_machine;
import com.xmlan.machine.module.led_machine.service.Led_machineService;
import com.xmlan.machine.module.xixun.util.InvokeBuildInJsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 上海熙讯屏幕调用汇总方法
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-07-24 11:13
 **/
@Controller
@RequestMapping("${adminPath}/xixunAD")
public class XixunAD extends BaseController {
    @Autowired
    private Led_machineService led_machineService;

    /**
     * 列表findList
     * @return
     */
    @RequestMapping(value = "/list/{pageNo}")
    public String list(){
        return "xixun/xixunAD";
    }

    /**
     * 展示列表
     * @param user_id
     * @return
     */
    @RequestMapping(value = "/findList")
    @ResponseBody
    public List<Led_machine> findList(@RequestParam("user_id") int user_id){
        if (user_id == 1){
            List<Led_machine> list = led_machineService.findAll();
            return list;
        }else {
            List<Led_machine> list = led_machineService.findAllByUserID(user_id);
            return list;
        }
    }

    /**
     * 新增led或者编辑界面
     * @param led_machine
     * @param model
     * @return
     */
    @RequestMapping(value = "/form")
    public String form(Led_machine led_machine,Model model,HttpServletRequest request){
        model.addAttribute( "led_machine", led_machine);
        model.addAttribute ("userList", UserCache.getDropdownUserList());
        model.addAttribute ("token", TokenUtils.getFormToken(request));
        return "xixun/xixunAdd";
    }

    /**
     * 保存led信息
     * @param ledMachine
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/save")
    public String save(@RequestBody Led_machine ledMachine, RedirectAttributes redirectAttributes){
        if (ledMachine.getId()==0) {
            led_machineService.insert(ledMachine);
            addMessage(redirectAttributes, "创建led屏幕成功");
        }else {
            led_machineService.update(ledMachine);
            addMessage(redirectAttributes, "修改led屏幕成功");
        }
        return "xixun/xixunAD";
    }

    /**
     * led详情页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public List detail(HttpServletRequest request){
        String idr = request.getParameter("id");
        int id = Integer.parseInt(idr);
        List<Led_machine> i = led_machineService.select(id);
        return i;
    }

    /**
     * 上传文件
     * @param files
     * @return
     */
    @RequestMapping(value = "/upload",method =RequestMethod.POST)
    public String upload(@RequestParam("files") MultipartFile[] files,HttpServletRequest request) throws IOException {
        Date date = new Date();
        String dataForm = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date);
        String filenameTemp;
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(httpRequest);
        String led = multipartRequest.getParameter("led");
        if(files!=null&&files.length>0){
            //循环获取file数组中得文件
            for(int i = 0;i<files.length;i++){
                MultipartFile file = files[i];
                //保存文件
               UploadUtils.saveFile(file, BaseBean.path);
            }
        }
        String file = files[0].getOriginalFilename();
        String [] fileName = FileUtils.getFileName(BaseBean.path);
        for(String name:fileName)
        {
            if (file.equals(name)){
//                String name1 = name.substring(0,name.indexOf("."));
                String name2 = name.substring(name.indexOf("."));
                filenameTemp= BaseBean.path+dataForm+".html";
                String call = BaseBean.XWALKURL+dataForm+".html";
                File filename = new File(filenameTemp);
                if (!filename.exists()) {
                    filename.createNewFile();
                }
                if (name2.equals(".mp4")){
                    boolean bea= FileUtils.writeToFile("<head><style>body{margin:0;padding:0;}</style><video loop=\"loop\" muted src=\""+name+"\" style=\"width: 128px;height: 256px\" controls=\"controls\" autoplay=\"autoplay\"></video></head>",filenameTemp);
                    if (bea == true){
                        CallXwalkFn callXwalkFn = new CallXwalkFn();
                        callXwalkFn.callXwalkFn(call,led);
                    }
                }else if (name2.equals(".png") || name2.equals(".jpg") || name2.equals(".jpeg")){
                    boolean bea= FileUtils.writeToFile("<head><style>body{margin:0;padding:0;}</style></head><img src=\""+name+"\" style=\"width: 128px;height: 256px\"/></head>",filenameTemp);
                    if (bea == true){
                        CallXwalkFn callXwalkFn = new CallXwalkFn();
                        callXwalkFn.callXwalkFn(call,led);
                    }
                }
            }
        }
        return "redirect:list/1";
    }


    /**
     * 删除led
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("id") int id){
        int i = led_machineService.delete(id);
        return "xixun/xixunAD";
    }

    /**
     * 更新led信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/update")
    public String update(@RequestParam("id") int id){
        int i = led_machineService.delete(id);
        return "xixun/xixunAD";
    }

    public String updateBack(){

        return "xixun/xixunAD";
    }

    /**
     * 启动第一步
     */
    @RequestMapping(value = "/loadUrl")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void loadUrl() {
        LoadUrl.main(null);
    }

    /**
     * 启动第二部
     */
    @RequestMapping(value = "/invokeJs")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void invokeJs() {
        InvokeJs.main(null);
    }

    /**
     * 清除屏幕广告
     */
    @RequestMapping(value = "/clear")
    public String clear(@RequestParam("led_code") String led_code) {
        Clear.main(new String[] {led_code});
        return "redirect:list/1";
    }

    /**
     * 滚动字幕
     */
    @RequestMapping(value = "/InvokeBuildInJs")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void InvokeBuildInJs(@RequestBody InvokeBuildInJsData inJsData) {
        InvokeBuildInJs inJs = new InvokeBuildInJs();
        inJs.invokeBuildInJs(inJsData);
    }

    /**
     * 图片素材转换为Base64
     * @param request
     */
    @RequestMapping(value = "/getBase64")
    @ResponseBody
    public void getBase64(HttpServletRequest request){
        String name = request.getParameter("name");
        String Base64 = request.getParameter("Base64");
        SaveStringFile.main(new String[] {name,Base64});
    }

    /**
     * 删除素材
     */
    @RequestMapping(value = "/deleteFile")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void deleteFile() {
        DeleteFile.main(null);
    }
    /**
     * 查询上传文件大小，验证是否成功上传
     */
//    @RequestMapping(value = "/getFileLength")
//    @org.springframework.web.bind.annotation.ResponseBody
//    public  void getFileLength() {
//        GetFileLength.main(null);
//    }

    /**
     * 设置播放列表
     */
    @RequestMapping(value = "/setPlayList")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void setPlayList() {
        DeleteFile.main(null);
    }
    /**
     * 开关屏幕
     */
    @RequestMapping(value = "/callCardService")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void callCardService() {
        CallCardService.main(null);
    }

    /**
     * 清除播放列表
     */
    @RequestMapping(value = "/clearPlayList")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void clearPlayList() {
        ClearPlayList.main(null);
    }

    /**
     * 启动xwalk
     */
    @RequestMapping(value = "/startActivity")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void startActivity() {
        StartActivity.main(null);
    }

    /**
     * 观看xwalk画面
     *
     */
//    @RequestMapping(value = "/callXwalkFn")
//    @org.springframework.web.bind.annotation.ResponseBody
//    public  void callXwalkFn() {
//        System.out.print("进入xwalk画面");
//        CallXwalkFn.main(null);
//    }
    /**
     * 获取屏幕实时画面
     */
    @RequestMapping(value = "/getPicture")
    @org.springframework.web.bind.annotation.ResponseBody
    public  String getPicture(HttpServletRequest request) {
        String led = request.getParameter("led");
//        GetPicture.main(null);
        GetPicture getPicture = new GetPicture();
        String s =getPicture.getStr(led);
        System.out.print(led);
        return s;
    }



}
