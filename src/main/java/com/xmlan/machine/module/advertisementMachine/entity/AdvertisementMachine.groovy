package com.xmlan.machine.module.advertisementMachine.entity

import org.apache.ibatis.type.Alias

/**
 * Created by ayakurayuki on 2017/12/12-11:18.
 * Package: com.xmlan.machine.module.advertisementMachine.entity
 */
@Alias("AdvertisementMachine")
class AdvertisementMachine {

    int id // 广告机ID
    String userID // 用户ID
    String name // 广告机名称
    String address // 地址
    String longitude // 经度
    String latitude // 纬度
    String addTime // 加入时间
    String codeNumber // 机器标识码(注册码)
    String remark // 备注

}
