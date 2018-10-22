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
     * 集中处理上传媒体信息然后重定向处理
     * @param file
     * @param redirectAttributes
     * @param request
     * @return
     */
//    @RequestMapping(value = "/uploadFile",method =RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes,HttpServletRequest request){
        String oldName = file.getOriginalFilename();
        int indexb = oldName.lastIndexOf(".");
        String type = oldName.substring(indexb);
        if (type.equals(".mp4")){
            return "redirect:downloadFileToLocal";
        }else if (type.equals(".png") || type.equals(".jpg") || type.equals(".jpeg")){
            return "redirect:upload";
        }else {
           return  "redirect:list/1";
        }
    }

    /**
     * 上传图片文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload",method =RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException {
        Date date = new Date();
        String dataForm = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date);
        String filenameTemp;

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(httpRequest);

        String led = multipartRequest.getParameter("led");
        UploadUtils.saveFile(dataForm,file, BaseBean.path);
        String fileName = UploadUtils.saveFile(dataForm,file, BaseBean.path);
        filenameTemp= BaseBean.path+"demo.html";
        File filename = new File(filenameTemp);
        if (filename.exists()) {
//            filename.createNewFile();
            if (filename.delete()){
                filename.createNewFile();
            }
        }
        boolean bea= FileUtils.writeToFile("<head><style>body{margin:0;padding:0;}</style></head><img src=\""+fileName+"\" style=\"width: 128px;height: 128px\"/></head>",filenameTemp);
        if (bea == true){
//                    CallXwalkFn callXwalkFn = new CallXwalkFn();
//                    callXwalkFn.callXwalkFn(call,led);
            LoadUrl loadUrl =new  LoadUrl();
            loadUrl.loadUrl(led);
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
    public  void loadUrl(@RequestParam("led_code") String led_code) {
        LoadUrl loadUrl =new  LoadUrl();
        loadUrl.loadUrl(led_code);
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
        Clear clear = new Clear();
        clear.clea(led_code);
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
     * 开关屏幕
     */
    @RequestMapping(value = "/callCardService")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void callCardService() {
        CallCardService.main(null);
    }

    /**
     * 设置播放列表
     */
//    @RequestMapping(value = "/setPlayList")
//    @org.springframework.web.bind.annotation.ResponseBody
    public  void setPlayList(String led,String fileName) {
        SetPlayList setPlayList = new SetPlayList();
        setPlayList.setPlayList(led,fileName);
    }
    /**
     * 清除播放列表
     */
    @RequestMapping(value = "/clearPlayList")
    @org.springframework.web.bind.annotation.ResponseBody
    public  String clearPlayList(@RequestParam(value = "led_code") String led) {
        ClearPlayList clearPlayList = new ClearPlayList();
        clearPlayList.clearPlayList(led);
        return "redirect:list/1";
    }


    /**
     * 启动xwalk
     */
    @RequestMapping(value = "/startActivity")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void startActivity(@RequestParam(value = "led") String led) {
        StartActivity.main(new String[] {led});
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

    /**
     * 媒体文件上传到led板卡内存
     * @param
     */
    @RequestMapping(value = "/downloadFileToLocal",method =RequestMethod.POST)
    public String downloadFileToLocal(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException{
        Date date = new Date();
        String dataForm = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date);
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(httpRequest);
        String led = multipartRequest.getParameter("led");
        String fileName = UploadUtils.saveFile(dataForm,file, BaseBean.path);
        DownloadFileToLocal downloadFileToLocal = new DownloadFileToLocal();
        downloadFileToLocal.DownloadFileToLocal(fileName,led);
        setPlayList(led,fileName);
        clear(led);
        return "redirect:list/1";
    }
}
