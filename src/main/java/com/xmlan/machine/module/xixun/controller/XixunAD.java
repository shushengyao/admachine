package com.xmlan.machine.module.xixun.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.cache.UserCache;
import com.xmlan.machine.common.util.FileUtils;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.common.util.UploadUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.entity.SimpleAdvertisementMachine;
import com.xmlan.machine.module.led_machine.entity.Led_machine;
import com.xmlan.machine.module.led_machine.service.Led_machineService;
import com.xmlan.machine.module.user.entity.User;
import com.xmlan.machine.module.xixun.util.InvokeBuildInJsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

    private ThreadPoolTaskExecutor taskExecutor;

    public XixunAD(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public XixunAD() {

    }

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
        List<Led_machine> list;
        if (user_id == 1){
            list = led_machineService.findAll();
        }else {
            list = led_machineService.findAllByUserID(user_id);
        }
        return list;
    }

    /**
     * 预查询led屏幕状态
     * @param leds
     * @return
     */
    @RequestMapping(value = "/ledStatus",method =RequestMethod.POST)
    @ResponseBody
    public Map<String,String> ledStatus(@RequestParam("leds[]")String[] leds){
        Map<String,String> hashMap = new HashMap<>();
        if (leds.length != NEW_ID){
            for (int i = 0; i < leds.length; i++) {
                String led = leds[i];
                String result;
                    result = getIsScreenOpen(led);
                    hashMap.put(led, result);
            }
        }
        return hashMap;
    }
    /**
     * 新增led或者编辑界面
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/form")
    public String form(Model model,HttpServletRequest request,ModelMap modelMap){
        User user= (User) modelMap.get("loginUser");
        Enumeration enu=request.getParameterNames();
        if(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+":"+request.getParameter(paraName));
            int id = Integer.parseInt(request.getParameter(paraName).replace(" ",""));
            Led_machine led_machine = led_machineService.getLEDByID(id);
            model.addAttribute( "led_machine", led_machine);
            model.addAttribute( "advertisementMachine", led_machine.getMachine_id());
        }else {
            Led_machine led_machine = new Led_machine();
            led_machine.setId(NEW_INSERT_ID);
            led_machine.setUser_id(user.getId());
            model.addAttribute( "led_machine", led_machine);
        }
        List<SimpleAdvertisementMachine> machineList = AdvertisementMachineCache.getDropdownAdvertisementMachineList();
        if (user.getId() == 1){
            model.addAttribute( "machineList",machineList );
        }else {
            Iterator<SimpleAdvertisementMachine> iterator = machineList.iterator();
            while(iterator.hasNext()){
                if (iterator.next().getId() != 1){
                    iterator.remove();
                }
            }
            model.addAttribute( "machineList",machineList );
        }
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
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, ModelMap modelMap) throws IOException {
        Date date = new Date();
        String dataForm = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date);
        String filenameTemp;

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(httpRequest);
        String led = multipartRequest.getParameter("led");

        String oldName = file.getOriginalFilename();
        int index = oldName.lastIndexOf(".");
        String type = oldName.substring(index);
        UploadUtils.saveFile(dataForm, file, BaseBean.path);
        String fileName = UploadUtils.saveFile(dataForm, file, BaseBean.path);
        User user = (User) modelMap.get("loginUser");
        String authname = user.getAuthname();
        filenameTemp = BaseBean.path + authname+"_"+led + ".html";
        File filename = new File(filenameTemp);
        ScreenWidth width = new ScreenWidth();
        ScreenHeight height = new ScreenHeight();
        String screenWidth = width.getScreenWidth(led);
        String screeHeight = height.getScreenHeight(led);
        if (type.equals(".mp4")){
            downloadFileToLocal(fileName,led,screenWidth,screeHeight);
        }else if (type.equals(".png") || type.equals(".jpg") || type.equals(".jpeg") || type.equals(".gif")) {

            if (!filename.exists()) {
                filename.createNewFile();
            }
            if (filename.delete()) {
                filename.createNewFile();
            }
            boolean bea = FileUtils.writeToFile("<head><style>body{margin:0;padding:0;}</style></head><img src=\"" + fileName + "\" style=\"width: " + screenWidth + "px;height: " + screeHeight + "px\"/></head>", filenameTemp);
            if (bea == true) {
//                    CallXwalkFn callXwalkFn = new CallXwalkFn();
//                    callXwalkFn.callXwalkFn(call,led);
                clear(led);
                LoadUrl loadUrl = new LoadUrl();
                loadUrl.loadUrl(led, authname);
            }
        }

        String play_list =fileName;

        led_machineService.updatePlayList(play_list,led);
        return "redirect:list/1";
    }
    /**
     * 媒体文件上传到led板卡内存
     * @param
     */
//    @RequestMapping(value = "/downloadFileToLocal",method =RequestMethod.POST)
    public String downloadFileToLocal(String fileName,String led,String screenWidth,String screeHeight){
        int width = Integer.parseInt( screenWidth);
        int height =Integer.parseInt(screeHeight);

        DownloadFileToLocal downloadFileToLocal = new DownloadFileToLocal();
        downloadFileToLocal.DownloadFileToLocal(fileName,led);
        setPlayList(led,fileName,width,height);
        clear(led);
        return "redirect:list/1";
    }
    /**
     * 批量上传图片文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploads",method =RequestMethod.POST)
    public String uploads(@RequestParam("file") MultipartFile file, HttpServletRequest request, ModelMap modelMap,Model model) throws IOException {
        //获取当前时间
        Date date = new Date();
        String dataForm = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date);
        //从file中获取传过来的leds
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(httpRequest.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(httpRequest);
        String leds[] = multipartRequest.getParameter("led").split(",");

        User user = (User) modelMap.get("loginUser");
        String authname = user.getAuthname();
        String led;
        HashMap<String,String> map = new HashMap<>();
        String oldName = file.getOriginalFilename();
        String fileName = UploadUtils.saveFile(dataForm, file, BaseBean.path);
        int index = oldName.lastIndexOf(".");
        String type = oldName.substring(index);
        if (leds.length !=NEW_ID){
            for (int i = 0;i<leds.length;i++){
                led = leds[i];
                if (led != "" || led.equals(null)){
                    if (upload(type,led,authname,fileName)){
                        map.put(led,"true");
                    }else {
                        map.put(led,"flase");
                    }
                }else {
                    map.put(led,"error");
                }
            }
        }else {
            map.put("LEDS","NULL");
        }
        model.addAttribute(map);
        return "redirect:list/1";
    }

    public boolean upload(String type,String led,String authname,String fileName)throws IOException{
        String filenameTemp = BaseBean.path + authname+"_"+led + ".html";
        File filename = new File(filenameTemp);
        ScreenWidth width = new ScreenWidth();
        ScreenHeight height = new ScreenHeight();
        String screenWidth = width.getScreenWidth(led);
        String screeHeight = height.getScreenHeight(led);
        if (type.equals(".mp4")){
            downloadFileToLocal(fileName,led,screenWidth,screeHeight);
        }else if (type.equals(".png") || type.equals(".jpg") || type.equals(".jpeg") || type.equals(".gif")) {

            if (!filename.exists()) {
                filename.createNewFile();
            }
            if (filename.delete()) {
                filename.createNewFile();
            }
            boolean bea = FileUtils.writeToFile("<head><style>body{margin:0;padding:0;}</style></head><img src=\"" + fileName + "\" style=\"width: " + screenWidth + "px;height: " + screeHeight + "px\"/></head>", filenameTemp);
            if (bea == true) {
                clear(led);
                LoadUrl loadUrl = new LoadUrl();
                loadUrl.loadUrl(led, authname);
            }
        }
        String play_list =fileName;
        boolean updatePlayList = led_machineService.updatePlayList(play_list,led);
        logger.info(updatePlayList);
        return true;
    }

    /**
     * 监测led屏幕开关
     * @param led
     * @return
     */
    public String getIsScreenOpen(String led){
        IsScreenOpen isScreenOpen = new IsScreenOpen();
        String result;
            result = isScreenOpen.getIsScreenOpen(led);
        return result;
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

    /**
     * 查询天气
     * @param args
     */
    public static void main(String[] args){

    }

    public String updateBack(){

        return "xixun/xixunAD";
    }

    /**
     * 启动第一步
     */
    @RequestMapping(value = "/loadUrl")
    @org.springframework.web.bind.annotation.ResponseBody
    public  void loadUrl(@RequestParam("led_code") String led_code,ModelMap modelMap) {
        String authname = (String)modelMap.get("loginUser");
        LoadUrl loadUrl =new  LoadUrl();
        loadUrl.loadUrl(led_code,authname);
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
    public  void setPlayList(String led,String fileName,int width,int height) {
        SetPlayList setPlayList = new SetPlayList();
        setPlayList.setPlayList(led,fileName,width,height);
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

}