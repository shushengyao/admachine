package com.xmlan.machine.module.advertisementMachine.service

import com.github.pagehelper.PageHelper
import com.google.common.collect.Lists
import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.module.advertisementMachine.dao.AdvertisementMachineDAO
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachineCount
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor
import com.xmlan.machine.module.user.entity.User
import org.apache.ibatis.annotations.Param
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestParam

/**
 * Created by ayakurayuki on 2017/12/13-08:53.
 * Package: com.xmlan.machine.module.advertisementMachine.service
 */
@Service("AdvertisementMachineService")
@Transactional(readOnly = true)
class AdvertisementMachineService extends BaseService<AdvertisementMachine, AdvertisementMachineDAO> {

    private static final String longitude = "longitude"
    private static final String latitude = "latitude"

    @Autowired
    private MachineSensorService machineSensorService

    private static boolean isInArea(String flag, String value, LinkedHashMap<String, String> area) {
        switch (flag) {
            case longitude:
                return value.toDouble() >= area.minLon.toDouble() && value.toDouble() <= area.maxLon.toDouble()
            case latitude:
                return value.toDouble() >= area.minLat.toDouble() && value.toDouble() <= area.maxLat.toDouble()
            default:
                return false
        }
    }

    private static boolean checkOperate(int operate) {
        return operate == 0 ? true : operate == 1
    }

    @Override
    int insert(AdvertisementMachine machine) {
        def responseCode = super.insert(machine)
        def sensorData = new MachineSensor()
        sensorData.machineID = getByCodeNumber(machine.codeNumber).id
        machineSensorService.insert(sensorData)
        return responseCode
    }

    @Override
    int delete(AdvertisementMachine machine) {
        def sensorData = machineSensorService.getByMachineID(machine.id)
        machineSensorService.delete(sensorData)
        return super.delete(machine)
    }



    int updateLocation(int id, String longitude, String latitude) {
        if (AdvertisementMachineCache.get(id) != null) {
            dao.updateLocation(id, longitude, latitude)
            return DONE
        } else {
            return NO_SUCH_ROW
        }
    }
    int updateAccessToken(String accessToken){
        dao.accessToken(accessToken)
        return DONE
    }

    AdvertisementMachine findIdByCameraSequence(String cameraSequence){
        return dao.findIdByCameraSequence(cameraSequence)
    }
    AdvertisementMachine getByCodeNumber(String codeNumber) {
        return dao.getByCodeNumber(codeNumber)
    }

    int lightControl(int id, int operate) {
        if (null == AdvertisementMachineCache.get(id)) {
            return NO_SUCH_ROW
        }
        if (!checkOperate(operate)) {
            return ERROR_REQUEST
        }
        def machine = AdvertisementMachineCache.get(id)
        machine.light = operate
        dao.lightControl(id, operate)
        return DONE
    }

    int chargeControl(int id, int operate) {
        if (null == AdvertisementMachineCache.get(id)) {
            return NO_SUCH_ROW
        }
        if (!checkOperate(operate)) {
            return ERROR_REQUEST
        }
        def machine = AdvertisementMachineCache.get(id)
        machine.charge = operate
        dao.chargeControl(id, operate)
        return DONE
    }

    int checkedControl(int id, int operate) {
        if (null == AdvertisementMachineCache.get(id)) {
            return NO_SUCH_ROW
        }
        if (!checkOperate(operate)) {
            return ERROR_REQUEST
        }
        def machine = AdvertisementMachineCache.get(id)
        machine.checked = operate
        dao.checkedControl(id, operate)
        return DONE
    }

    static List<AdvertisementMachineCount> getMachineCountByUserID(List<User> list) {
        List<AdvertisementMachineCount> counts = Lists.newArrayList()
        list.each {
            AdvertisementMachineCount count = new AdvertisementMachineCount()
            count.id = it.id
            count.count = AdvertisementMachineCache.getMachineCount(it.id)
            counts.add(count)
        }
        return counts
    }
    List<AdvertisementMachine> findListByUserID(@RequestParam("userID") int userID,@RequestParam("pageNo") int pageNo){
        PageHelper.startPage pageNo, pageSize
        dao.findListByUserID(userID)
    }
    List<AdvertisementMachine> findListByUserID(@RequestParam("userID") int userID){
        dao.findListByUserID(userID)
    }

    /**
     * 提供给服务接口的列表查询
     * @param advertisementMachine 查询条件，条件是按照用户ID查询
     * @return 查询列表
     */
    static List<AdvertisementMachine> findForProvider(AdvertisementMachine advertisementMachine) {
//        List<AdvertisementMachine> machineList = AdvertisementMachineCache.advertisementMachineList
        List<AdvertisementMachine> filteredList = Lists.newArrayList()

//        machineList.each {
//            if (it.userID == advertisementMachine.userID) {
//                filteredList.add(it)
//            }
//        }
        return filteredList
    }

    /**
     * 根据两点确定范围内的广告机位置
     * @param minLon min longitude
     * @param maxLon max longitude
     * @param minLat min latitude
     * @param maxLat max latitude
     * @return 返回过滤后的广告机列表
     */
    static List<AdvertisementMachine> positionQuery(String minLon, String maxLon, String minLat, String maxLat) {
        List<AdvertisementMachine> list = AdvertisementMachineCache.advertisementMachineList
        // 根据范围条件过滤选中广告机。
        List<AdvertisementMachine> filteredList = Lists.newArrayList()
        list.each {
            def area = ["minLon": minLon, "minLat": minLat, "maxLon": maxLon, "maxLat": maxLat]
            if (isInArea(longitude, it.longitude, area)) {
                if (isInArea(latitude, it.latitude, area)) {
                    filteredList.add(it)
                }
            }
        }
        // 根据经纬度排序，先排经度再排纬度。这里采用List双条件排序。
        filteredList.sort { l, r ->
            if (l.longitude.toDouble() < r.longitude.toDouble()) {
                return -1
            } else if (l.longitude.toDouble() == r.longitude.toDouble()) {
                l.latitude.toDouble() <=> r.latitude.toDouble()
            } else {
                return 1
            }
        }
        return filteredList
    }
    List<AdvertisementMachine> findAll(){
        dao.findAll()
    }

    /**
     * 查询所有设备
     * @return
     */
    List<AdvertisementMachine> findAllMachine(){
        dao.findAllMachine()
    }
    /**
     * 普通用户查询设备列表
     * @return
     */
    List<AdvertisementMachine> findOrdinaryADList(){
        dao.findOrdinaryADList()
    }

    /**
     * 管理员用户通过id查询设备数据
     * @param id
     * @return
     */
    List<AdvertisementMachine> adchineListByUserID(@RequestParam("userID") int userID,@RequestParam("pageNo") int pageNo){
        PageHelper.startPage pageNo, pageSize
        dao.adchineListByUserID(userID)
    }
    List<AdvertisementMachine> adchineListByUserID(@RequestParam("userID") int userID){
        dao.adchineListByUserID(userID)
    }
    /**
     * 普通用户根据id查询设备列表
     * @param userID
     * @return
     */
    List<AdvertisementMachine> generalFindList(@RequestParam("userID") int userID,@RequestParam("pageNo") int pageNo){
        PageHelper.startPage pageNo, pageSize
        dao.generalFindList(userID)
    }

    List<AdvertisementMachine> generalFindList(@RequestParam("userID") int userID){
        dao.generalFindList(userID)
    }

    /**
     * 管理员把设备添加给新的用户
     * @param machine_id
     * @param user_id
     * @return
     */
    int insertMachineToUser(int machine_id,int user_id){
        dao.insertMachineToUser(machine_id,user_id)
    }

    /**
     * 管理员把设备从用户名下删除
     * @param user_id
     * @return
     */
    int deleteMachineForUser(@RequestParam("user_id") int user_id){
        dao.deleteMachineForUser(user_id)
    }

}
