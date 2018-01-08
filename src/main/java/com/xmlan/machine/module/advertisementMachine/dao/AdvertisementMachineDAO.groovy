package com.xmlan.machine.module.advertisementMachine.dao

import com.xmlan.machine.common.annotation.MyBatisDAO
import com.xmlan.machine.common.base.BaseDAO
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import org.apache.ibatis.annotations.Param

/**
 * Created by ayakurayuki on 2017/12/13-08:52.
 * Package: com.xmlan.machine.module.advertisementMachine.dao
 */
@MyBatisDAO
interface AdvertisementMachineDAO extends BaseDAO<AdvertisementMachine> {

    /**
     * 查看该用户的所有灯杆（广告机）
     *
     * @return 该用户的所有灯杆列表
     */
    List<AdvertisementMachine> findAllADMachineByUser(@Param("userID") int userID)

    AdvertisementMachine getByCodeNumber(@Param("codeNumber") String codeNumber)

}
