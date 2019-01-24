package com.xmlan.machine.module.singlelamp_new.entity;

/**
 * @program: admachine
 * @description: entity
 * @author: YSS
 * @create: 2019-01-11 10:02
 **/
public class SingLampNew {
    private int id;//主键id
    private int machineID;//灯杆id
    private int userID;//用户id
    private String updateTime;//更新时间
    private double deviceCode;//设备码
    private double temperature;//电源温度
    private double inputVoltage;//输入电压
    private double inputCurrent;//输入电流
    private double outputVol;//输出电压
    private double outputCurr;//输出电流
    private double gridAP;//电源有功功率
    private double gridAPD;//电源有功功率因素
    private double ledBright;//led亮度
    private String powerStatus;//电源状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public double getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(double deviceCode) {
        this.deviceCode = deviceCode;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getInputVoltage() {
        return inputVoltage;
    }

    public void setInputVoltage(double inputVoltage) {
        this.inputVoltage = inputVoltage;
    }

    public double getInputCurrent() {
        return inputCurrent;
    }

    public void setInputCurrent(double inputCurrent) {
        this.inputCurrent = inputCurrent;
    }

    public double getOutputVol() {
        return outputVol;
    }

    public void setOutputVol(double outputVol) {
        this.outputVol = outputVol;
    }

    public double getOutputCurr() {
        return outputCurr;
    }

    public void setOutputCurr(double outputCurr) {
        this.outputCurr = outputCurr;
    }

    public double getGridAP() {
        return gridAP;
    }

    public void setGridAP(double gridAP) {
        this.gridAP = gridAP;
    }

    public double getGridAPD() {
        return gridAPD;
    }

    public void setGridAPD(double gridAPD) {
        this.gridAPD = gridAPD;
    }

    public double getLedBright() {
        return ledBright;
    }

    public void setLedBright(double ledBright) {
        this.ledBright = ledBright;
    }

    public String getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(String powerStatus) {
        this.powerStatus = powerStatus;
    }
}
