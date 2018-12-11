package com.xmlan.machine.module.singLamp.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xmlan.machine.common.base.BaseService;
import com.xmlan.machine.module.singLamp.dao.SingLampDAO;
import com.xmlan.machine.module.singLamp.entity.SingLamp;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: admachine
 * @description: service
 * @author: YSS
 * @create: 2018-11-07 13:44
 **/
@Service("SingLampService")
public class SingLampService extends BaseService<SingLamp,SingLampDAO> {
    private SingLampDAO singLampDAO;

    public SingLampService(SingLampDAO singLampDAO) {
        this.singLampDAO = singLampDAO;
    }

    /**
     * 根据用户id查询数据列表
     * @param userID
     * @param pageNo
     * @return
     */
   public PageInfo<SingLamp> findListByUserID(@RequestParam("userID") int userID, @RequestParam("pageNo") int pageNo){
       int pageSize = 10;
        PageHelper.startPage (pageNo, pageSize);
        PageInfo<SingLamp> pageList = new PageInfo<>(singLampDAO.findListByUserID(userID));
        return pageList;
    }
    public List<SingLamp> findListByUserID(@RequestParam("userID") int userID){
        return singLampDAO.findListByUserID(userID);
    }

    /**
     * 查询所有
     * @param pageNo
     * @return
     */
    public PageInfo<SingLamp> findAll(@RequestParam("pageNo") int pageNo){
        int pageSize = 10;
        PageHelper.startPage (pageNo, pageSize);
        PageInfo<SingLamp> pageList = new PageInfo<>(singLampDAO.findAll());
        return pageList;
    }

    /**
     * 根据单灯控制器id查询信息
     * @param id
     * @return
     */
    public List<SingLamp> findListByID(@RequestParam("id") int id){
        List<SingLamp> list = singLampDAO.findListByID(id);
        return list;
    }

    /**
     * 根据设备id查询对应的单灯控制
     * @param machineID
     * @return
     */
    public List<SingLamp> findListByMachineID(@RequestParam("machineID") int machineID){
        List<SingLamp> list = singLampDAO.findListByMachineID(machineID);
        return list;
    }

    /**
     * 新增单灯控制器并返回其id
     * @param singLamp
     * @return
     */
    public int insertSingLamp(SingLamp singLamp){
        int id = singLampDAO.insertSingLamp(singLamp);
        return id;
    }
}
