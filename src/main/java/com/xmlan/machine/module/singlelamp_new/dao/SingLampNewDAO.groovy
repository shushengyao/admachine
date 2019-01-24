package com.xmlan.machine.module.singlelamp_new.dao;

import com.xmlan.machine.common.base.BaseDAO;
import com.xmlan.machine.module.singlelamp_new.entity.SingLampNew;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: admachine
 * @description: dao
 * @author: YSS
 * @create: 2019-01-11 10:56
 **/
@Repository
interface SingLampNewDAO extends BaseDAO<SingLampNew> {
    @Override
    List<SingLampNew> findAll();

    List<SingLampNew> findListByUserID(@Param("userID") int userID);

    List<SingLampNew> findListByID(@Param("id") int id);

    List<SingLampNew> findListByMachineID(@Param("machineID") int machineID);

    int insertSingLamp(SingLampNew singLamp);
}
