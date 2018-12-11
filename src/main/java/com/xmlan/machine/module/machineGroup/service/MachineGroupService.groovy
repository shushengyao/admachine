package com.xmlan.machine.module.machineGroup.service

import com.github.pagehelper.PageHelper;
import com.xmlan.machine.common.base.BaseService;
import com.xmlan.machine.module.machineGroup.dao.MachineGroupDAO;
import com.xmlan.machine.module.machineGroup.entity.MachineGroup;
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: admachine
 * @description: service
 * @author: YSS
 * @create: 2018-09-28 14:14
 **/
@Service("MachineGroupService")
class MachineGroupService extends BaseService<MachineGroup,MachineGroupDAO> {

     List<MachineGroup> findAll(int pageNo){
       PageHelper.startPage pageNo, pageSize
       dao.findAll();
     }

    List<MachineGroup> findAllByUserID(@RequestParam("userID") int userID,@RequestParam("pageNo") int pageNo){
        PageHelper.startPage pageNo, pageSize
        dao.findAllByUserID(userID);
    }

    int deleteGroup(int id){
        dao.deleteGroup(id);
    }
}
