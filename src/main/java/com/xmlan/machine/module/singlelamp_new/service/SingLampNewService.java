package com.xmlan.machine.module.singlelamp_new.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xmlan.machine.common.base.BaseService;
import com.xmlan.machine.module.singlelamp_new.dao.SingLampNewDAO;
import com.xmlan.machine.module.singlelamp_new.entity.SingLampNew;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 新单灯控制器方法层
 * @program: admachine
 * @description: service
 * @author: YSS
 * @create: 2019-01-11 13:45
 **/
@Service("SingLampNewService")
public class SingLampNewService extends BaseService<SingLampNew,SingLampNewDAO> {
    private SingLampNewDAO singLampNewDAO;

    public SingLampNewService(SingLampNewDAO singLampNewDAO){
        this.singLampNewDAO = singLampNewDAO;
    }

    /**
     * 根据用户id查询数据列表
     * @param userID
     * @param pageNo
     * @return
     */
    public PageInfo<SingLampNew> findListByUserID(@RequestParam("userID") int userID, @RequestParam("pageNo") int pageNo){
        int pageSize = 10;
        PageHelper.startPage (pageNo, pageSize);
        PageInfo<SingLampNew> pageList = new PageInfo<>(singLampNewDAO.findListByUserID(userID));
        return pageList;
    }
    public List<SingLampNew> findListByUserID(@RequestParam("userID") int userID){
        return singLampNewDAO.findListByUserID(userID);
    }

    /**
     * 查询所有
     * @param pageNo
     * @return
     */
    public PageInfo<SingLampNew> findAll(@RequestParam("pageNo") int pageNo){
        int pageSize = 10;
        PageHelper.startPage (pageNo, pageSize);
        PageInfo<SingLampNew> pageList = new PageInfo<>(singLampNewDAO.findAll());
        return pageList;
    }

    /**
     * 根据单灯控制器id查询信息
     * @param id
     * @return
     */
    public List<SingLampNew> findListByID(@RequestParam("id") int id){
        List<SingLampNew> list = singLampNewDAO.findListByID(id);
        return list;
    }

    /**
     * 根据设备id查询对应的单灯控制
     * @param machineID
     * @return
     */
    public List<SingLampNew> findListByMachineID(@RequestParam("machineID") int machineID){
        List<SingLampNew> list = singLampNewDAO.findListByMachineID(machineID);
        return list;
    }

    /**
     * 新增单灯控制器并返回其id
     * @param singLamp
     * @return
     */
    public int insertSingLamp(SingLampNew singLamp){
        int id = singLampNewDAO.insertSingLamp(singLamp);
        return id;
    }
}
