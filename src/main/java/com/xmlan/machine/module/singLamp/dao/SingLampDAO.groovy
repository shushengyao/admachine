package com.xmlan.machine.module.singLamp.dao;

import com.xmlan.machine.common.base.BaseDAO;
import com.xmlan.machine.module.singLamp.entity.SingLamp
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: admachine
 * @description: dao
 * @author: YSS
 * @create: 2018-11-07 10:44
 **/
@Repository
interface SingLampDAO extends BaseDAO<SingLamp> {
    @Override
    List<SingLamp> findAll();

    List<SingLamp> findListByUserID(@Param("userID") int userID)

    List<SingLamp> findListByID(@Param("id") int id)

    List<SingLamp> findListByMachineID(@Param("machineID") int machineID)

    int insertSingLamp(SingLamp singLamp)

}
