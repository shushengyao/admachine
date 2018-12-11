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
    String pm10
    String eCo2
    String tVoc
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

    String getPm10() {
        return pm10
    }

    void setPm10(String pm10) {
        this.pm10 = pm10
    }

    String geteCo2() {
        return eCo2
    }

    void seteCo2(String eCo2) {
        this.eCo2 = eCo2
    }

    String gettVoc() {
        return tVoc
    }

    void settVoc(String tVoc) {
        this.tVoc = tVoc
    }
}
