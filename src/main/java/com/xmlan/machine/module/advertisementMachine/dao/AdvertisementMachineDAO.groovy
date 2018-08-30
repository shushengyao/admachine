package com.xmlan.machine.module.advertisementMachine.dao

import com.xmlan.machine.common.base.BaseDAO
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

/**
 * 广告机DAO <br/>
 * Created by ayakurayuki on 2017/12/13-08:52. <br/>
 * Package: com.xmlan.machine.module.advertisementMachine.dao <br/>
 */
@Repository
interface AdvertisementMachineDAO extends BaseDAO<AdvertisementMachine> {


    /**
     * 查看该用户的所有灯杆 (广告机)
     * @return 该用户的所有灯杆列表
     */
    List<AdvertisementMachine> findListByUserID(@Param("userID") int userID)

    /**
     * 通过机器码获取广告机
     * @param codeNumber 机器识别码
     * @return 广告机
     */
    AdvertisementMachine getByCodeNumber(@Param("codeNumber") String codeNumber)

    /**
     * 根据摄像头序列号查设备
     * @param cameraSequence
     * @return
     */
    AdvertisementMachine findIdByCameraSequence(@Param("cameraSequence") String cameraSequence)

    /**
     * 更新定位坐标经纬度
     * @param longitude 经度
     * @param latitude 纬度
     * @return 操作返回码
     */
    int updateLocation(@Param("id") int id, @Param("longitude") String longitude, @Param("latitude") String latitude)

    /**
     * 更新摄像头的accessToken
     * @param accessToken
     * @return
     */
    int accessToken(@Param("accessToken") String accessToken)
    /**
     * 灯光控制
     * @param id 广告机ID
     * @param operate 操作码 (0/1)
     * @return 操作返回码
     */
    int lightControl(@Param("id") int id, @Param("operate") int operate)

    /**
     * 充电控制
     * @param id 广告机ID
     * @param operate 操作码 (0/1)
     * @return 操作返回码
     */
    int chargeControl(@Param("id") int id, @Param("operate") int operate)

    /**
     * 选中控制
     * @param id 广告机ID
     * @param operate 操作码 (0/1)
     * @return 操作返回码
     */
    int checkedControl(@Param("id") int id, @Param("operate") int operate)
    /**
     * 根据id查询设备
     * @return
     */
    /**
     * 查询所有设备
     * @return
     */
    List<AdvertisementMachine> findAll(advertisementMachine)

    List<AdvertisementMachine> findAll()
    /**
     * 普通用户查询设备列表
     * @param userID
     * @return
     */
    List<AdvertisementMachine> findOrdinaryADList(@Param("userID") int userID)

    List<AdvertisementMachine> findAllMachine()

    /**
     * 管理员用户查询
     * @param userID
     * @return
     */
    List<AdvertisementMachine> adchineListByUserID(advertisementMachine)

    List<AdvertisementMachine> adchineListByUserID(@Param("userID") int userID)

    List<AdvertisementMachine> atmosphereListByUserID(@Param("userID") int userID)
    /**
     * 验证设备是否重复
     * @param userID
     * @return
     */
    List<AdvertisementMachine> findRepeat(@Param("userID") int userID)

    /**
     * 普通用户查询
     * @param userID
     * @return
     */
    List<AdvertisementMachine> generalFindList(@Param("userID") int userID)

    /**
     * 把设备添加新的用户
     * @param machine
     * @param user_id
     * @return
     */
    int insertMachineToUser(@Param("machine_id") int machine,@Param("user_id") int user_id)

    /**
     * 管理员把设备从用户名下删除
     * @param user_id
     * @return
     */
    int deleteMachineForUser(@Param("user_id") int user_id)


}
