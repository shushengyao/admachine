package com.xmlan.machine.module.advertisementMachine.dao

import com.xmlan.machine.common.base.BaseDAO
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

/**
 * 天气传感器DAO <br/>
 * Created by ayakurayuki on 2018/1/5-15:31. <br/>
 * Package: com.xmlan.machine.module.advertisementMachine.dao <br/>
 */
@Repository
interface MachineSensorDAO extends BaseDAO<MachineSensor> {

    /**
     * 通过广告机ID获取天气信息
     * @param machineID 广告机ID
     * @return
     */
    MachineSensor getByMachineID(@Param("machineID") int machineID)

    List<MachineSensor> findAll()

}
