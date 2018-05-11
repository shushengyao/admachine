package com.xmlan.machine.module.advertisementMachine.entity

/**
 * 天气传感器类 <br/>
 * Created by ayakurayuki on 2018/1/5-15:30. <br/>
 * Package: com.xmlan.machine.module.advertisementMachine.entity <br/>
 */
class MachineSensor implements Serializable {

    int id
    String temperature
    String humidity
    String pm25
    String brightness
    String machineID

    int getId() {
        return id
    }

    String getTemperature() {
        return temperature
    }

    String getHumidity() {
        return humidity
    }

    String getPm25() {
        return pm25
    }

    void setId(int id) {
        this.id = id
    }

    void setTemperature(String temperature) {
        this.temperature = temperature
    }

    void setHumidity(String humidity) {
        this.humidity = humidity
    }

    void setPm25(String pm25) {
        this.pm25 = pm25
    }

    void setBrightness(String brightness) {
        this.brightness = brightness
    }

    void setMachineID(String machineID) {
        this.machineID = machineID
    }

    String getBrightness() {

        return brightness
    }

    String getMachineID() {
        return machineID
    }
}
