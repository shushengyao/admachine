package com.xmlan.machine.module.led_machine.service
import org.springframework.stereotype.Service
import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.module.led_machine.dao.Led_machineDAO;
import com.xmlan.machine.module.led_machine.entity.Led_machine

import java.util.List;

/**
 * @program: admachine
 * @description: service
 * @author: YSS
 * @create: 2018-07-27 10:26
 **/
@Service("Led_machineService")
class Led_machineService extends BaseService<Led_machine,Led_machineDAO> {
    /**
     * 根据用户id查询led广告列表
     * @param machine_id
     * @return
     */
    List<Led_machine> findAllByUserID (int user_id){
        dao.findAllByUserID(user_id)
    }
    int delete(int id){
        int result = dao.delete(id)
        return result
    }
    List<Led_machine> select(int id){
        dao.select(id)
    }

    List<Led_machine> findAll(){
        dao.findAll()
    }

}
