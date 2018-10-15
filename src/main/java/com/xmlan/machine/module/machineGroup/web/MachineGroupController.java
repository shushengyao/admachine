package com.xmlan.machine.module.machineGroup.web;

import com.alibaba.fastjson.JSON;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.SessionUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.advertisementMachine.service.MachineSensorService;
import com.xmlan.machine.module.machineGroup.entity.MachineGroup;
import com.xmlan.machine.module.machineGroup.service.MachineGroupService;
import com.xmlan.machine.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @RequestMapping(value ="/groupList")
    @ResponseBody
    public List<MachineGroup> groupList(){
//        List<MachineGroup> machineGroupList = service.findAll();machineList
        List<MachineGroup> machineGroupList = service.findAllByUserID(6);
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

}
