package com.xmlan.machine.module.advertisementMachine.entity

import org.apache.ibatis.type.Alias

/**
 * Created by ayakurayuki on 2018/1/5-15:30.
 * Package: com.xmlan.machine.module.advertisementMachine.entity
 */
@Alias("MachineSensor")
class MachineSensor implements Serializable {

    int id
    String temperature
    String humidity
    String pm25
    String pm10
    String codeNumber

}
