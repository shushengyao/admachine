package com.xmlan.machine.module.machineGroup.service;

import com.xmlan.machine.common.base.BaseService;
import com.xmlan.machine.module.machineGroup.dao.MachineGroupDAO;
import com.xmlan.machine.module.machineGroup.entity.MachineGroup;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: admachine
 * @description: service
 * @author: YSS
 * @create: 2018-09-28 14:14
 **/
@Service("MachineGroupService")
class MachineGroupService extends BaseService<MachineGroup,MachineGroupDAO> {

     List<MachineGroup> findAll(){
       dao.findAll();
     }

    List<MachineGroup> findAllByUserID(int userID){
        dao.findAllByUserID(userID);
    }
}
