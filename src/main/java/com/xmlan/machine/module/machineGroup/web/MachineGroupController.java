package com.xmlan.machine.module.machineGroup.web;

import com.alibaba.fastjson.JSON;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.advertisementMachine.service.MachineSensorService;
import com.xmlan.machine.module.machineGroup.entity.MachineGroup;
import com.xmlan.machine.module.machineGroup.service.MachineGroupService;
import com.xmlan.machine.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-09-28 14:18
 **/
@Controller
@RequestMapping("${adminPath}/machineGroup")
public class MachineGroupController extends BaseController {
    @Autowired
    private MachineGroupService service;

    @Autowired
    private AdvertisementMachineService machineService;

    /**
     *  查询所有分组
     * @return
     */
    @RequestMapping(value ="/groupList/{pageNo}")
    @ResponseBody
    public List<MachineGroup> groupList(@PathVariable int pageNo,ModelMap modelMap){
        User user=(User)modelMap.get("loginUser");
        int userID = user.getId();
        List<MachineGroup> machineGroupList;
//        List<MachineGroup> machineGroupList = service.findAll();machineList
        if (userID ==1 || userID == 10) {
            machineGroupList = service.findAll(pageNo);
        }else {
            machineGroupList = service.findAllByUserID(userID,pageNo);
        }
        return machineGroupList;
    }

    @RequestMapping("/machineList/{pageNo}")
    public void machineList(HttpServletResponse response, HttpServletRequest request, AdvertisementMachine advertisementMachine, ModelMap modelMap,@PathVariable int pageNo){
        User user=(User)modelMap.get("loginUser");
        int userID = user.getId();
        advertisementMachine.setUserID(userID);
        List<AdvertisementMachine> machineGroupList;
        if (SessionUtils.getAdmin(request).getRoleID() != ADMIN_ROLE_ID) {
            advertisementMachine.setUserID(SessionUtils.getAdmin(request).getId());
        }
        if (userID ==1 || userID == 10) {
            machineGroupList = machineService.findAll(advertisementMachine,pageNo);
        }else {
            machineGroupList = machineService.adchineListByUserID(advertisementMachine,pageNo);
        }
        String jsonResult = JSON.toJSONString(machineGroupList);
        renderData(response, jsonResult);
    }
    private void renderData(HttpServletResponse response, String data) {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.print(data);
        } catch (IOException ex) {
            Logger.getLogger(MachineGroupController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    /**
     * 保存
     * @return
     */
    @RequestMapping(value = "/save")
    public String save(MachineGroup machineGroup,RedirectAttributes redirectAttributes){
        service.insert(machineGroup);
        MachineGroupController controller = new MachineGroupController();
        controller.addMessage(redirectAttributes,"设置成功！");
        return "baiduMap/baiduMap";
    }

    /**
     * 分组添加设备
     * @param ids
     * @param name
     * @param modelMap
     */
    @RequestMapping(value = "/adddGroup")
    @ResponseBody
    HashMap<String,String> adddGroup(@RequestParam("ids") String ids, @RequestParam("name") String name, ModelMap modelMap, MachineGroup machineGroup ){
        HashMap<String,String> map = new HashMap<>();
        Object object = modelMap.get("loginUser");
        User user = (User) object;
        int userID = user.getId();
        if (!ids.isEmpty() && !ids.equals("") && !name.equals("") && !name.isEmpty()){
            machineGroup.setMachineID(ids);
            machineGroup.setUserID(userID);
            machineGroup.setGroupName(name);
//            service.insert(machineGroup);
            map.put("result","sucess");
        }else {
            map.put("result","error");
        }
        return map;
    }

    /**
     * 删除分组
     * @param
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    int delete(@RequestParam("id") int id){
        int result = 0;
        if (id > NEW_ID){
            result = service.deleteGroup(id);
        }
        return result;
    }

}
