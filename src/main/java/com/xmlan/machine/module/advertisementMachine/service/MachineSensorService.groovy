package com.xmlan.machine.module.advertisementMachine.service

import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.module.advertisementMachine.dao.MachineSensorDAO
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor
import org.springframework.stereotype.Service

/**
 * Created by ayakurayuki on 2018/1/5-15:40.
 * Package: com.xmlan.machine.module.advertisementMachine.service
 */
@Service("MachineSensorService")
class MachineSensorService extends BaseService<MachineSensor, MachineSensorDAO> {

    MachineSensor getByMachineID(int id) {
        return dao.getByMachineID(id)
    }

}
