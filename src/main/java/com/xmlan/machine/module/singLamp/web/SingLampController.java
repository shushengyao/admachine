package com.xmlan.machine.module.singLamp.web;

import com.github.pagehelper.PageInfo;
import com.xmlan.machine.common.base.BaseBean;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.DateUtils;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.singLamp.entity.SingLamp;
import com.xmlan.machine.module.singLamp.service.SingLampService;
import com.xmlan.machine.module.user.entity.User;
import com.xmlan.machine.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * 单灯控制器数据控制类
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-11-07 13:55
 **/
@Controller
@RequestMapping("${adminPath}/singLamp")
public class SingLampController extends BaseController {
    @Autowired
    private SingLampService singLampService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdvertisementMachineService machineService;

    /**
     *查询数据详情
     * @return
     */
//    @RequestMapping(value = "/details/{id}")
//    @ResponseBody
//    public PageInfo<SingLamp> list(){
//        PageInfo<SingLamp> list = singLampService.findListByUserID(1,1);
//        return list;
//    }

    /**
     * 数据列表
     * @param model
     * @param modelMap
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/list/{pageNo}")
    public String singLampList(Model model,ModelMap modelMap,@PathVariable int pageNo){
        User user=(User)modelMap.get("loginUser");
        int userID = user.getId();
        PageInfo<SingLamp> list;
        if (userID ==1 || userID == 10){
            list = singLampService.findAll(pageNo);
        }else {
            list = singLampService.findListByUserID(userID,pageNo);
        }
        model.addAttribute("singLampDetail",list.getList());
        model.addAttribute("page",list);
        return "advertisementMachine/singLampList";
    }

    /**
     * 编辑单灯控制器信息
     * @return
     */
    @RequestMapping(value = "/form/{id}")
    public String form(Model model,@PathVariable int id){
        List<User> userList =userService.findAll();
        List<AdvertisementMachine> machineList = machineService.findAll();
        List<SingLamp> singLamp = singLampService.findListByID(id);
        AdvertisementMachine machine = new AdvertisementMachine();
        model.addAttribute("id",id);
        model.addAttribute("userList",userList);
        model.addAttribute("singLamp",singLamp);
        model.addAttribute("machineList",machineList);
        return "advertisementMachine/singLampForm";
    }

    /**
     * 查询设备的单灯控制器数据
     * @return
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public List<SingLamp> singLampDetail(@PathVariable int id,RedirectAttributes redirectAttributes){
        List<SingLamp> list = new ArrayList<>();
        try {
            list = singLampService.findListByMachineID(id);
            if (list.size() == 0){
                super.addMessage(redirectAttributes,"该设备无单灯控制器数据");
            }
        }catch (Exception e){
            super.addMessage(redirectAttributes,"该设备无单灯控制器数据");
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 保存/更新单灯控制器数据
     * @param singLamp
     * @param
     * @return
     */
    @RequestMapping(value = "/save/{id}")
    String save(SingLamp singLamp){
        singLamp.setUpdateTime(DateUtils.getDateTime());
        if (singLamp.getId() == NEW_ID){
            singLampService.insertSingLamp(singLamp);
        }else {
            singLampService.update(singLamp);
        }
        if (singLamp.getMachineID() != NEW_ID){
            machineService.updateSingLampID(singLamp.getMachineID(),singLamp.getId());
        }
        return "redirect:http://localhost:8888/mng/singLamp/list/1";
    }
}
