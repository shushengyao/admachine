package com.xmlan.machine.module.machineGroup.dao;

import com.xmlan.machine.common.base.BaseDAO;
import com.xmlan.machine.module.machineGroup.entity.MachineGroup
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: admachine
 * @description: dao
 * @author: YSS
 * @create: 2018-09-28 10:54
 **/
@Repository
interface MachineGroupDAO extends BaseDAO<MachineGroup> {
    List<MachineGroup> findAll()

    List<MachineGroup> findAllByUserID(int userID)

    int delete(@RequestParam("userID") int userID)
}
