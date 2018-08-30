package com.xmlan.machine.module.advertisement.dao

import com.xmlan.machine.common.base.BaseDAO
import com.xmlan.machine.module.advertisement.entity.Advertisement
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestParam

/**
 * yss
 * <p>
 * Package: com.xmlan.machine.module.advertisement.dao
 * <p>
 * 广告DAO
 */
@Repository
interface AdvertisementDAO extends BaseDAO<Advertisement> {
    /**
     * 根据用户创建人查询广告列表
     * @param founder
     * @return
     */
    List<Advertisement> findListByUserID(@RequestParam("userID") int userID)

    List<Advertisement> findAll()

    /**
     *
     * @param user_id
     * @return
     */
    List<Advertisement> findMachineByUserID(@RequestParam("user_id") int user_id)

    /**
     * 根据设备id查询广告
     * @param machine_id
     * @return
     */
    List<Advertisement> findListByMachineID(@RequestParam("machine_id") int machine_id)
    /**
     * 更改广告所属设备
     * @param userID
     * @return
     */
    int updateUserID(@Param("userID") int userID,@Param("machineID") int machineID)
}
