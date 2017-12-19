package com.xmlan.machine.module.advertisementMachine.service;

import com.xmlan.machine.common.base.BaseService;
import com.xmlan.machine.module.advertisementMachine.dao.AdvertisementMachineDAO;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ayakurayuki on 2017/12/13-08:53.
 * Package: com.xmlan.machine.module.advertisementMachine.service
 */
@Service("AdvertisementMachineService")
@Transactional(readOnly = true)
public class AdvertisementMachineService extends BaseService<AdvertisementMachine, AdvertisementMachineDAO> {

    @Override
    public int delete(AdvertisementMachine entity) {
        if (entity.getId() == 1) {
            return DATABASE_DO_NOTHING;
        }
        return super.delete(entity);
    }

}
