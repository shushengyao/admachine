package com.xmlan.machine.module.singLamp.entity;

/**
 * 单灯控制器的数据
 * @program: admachine
 * @description: entity
 * @author: YSS
 * @create: 2018-11-07 10:28
 **/
public class SingLamp {
    private int id;//主键ID
    private int machineID;//设备ID
    private int userID;//用户ID
    private String updateTime;//更新时间
    private double currError;//故障位
    private double gridVolt;//电压
    private double gridCurr;//电流
    private double gridFreq;//频率
    private double gridPF;//电网PF
    private double gridAP;//电网有功
    private double gridRP;//电网无功
    private double temperature;//温度
    private double ledDim;//亮灯
    private double workTime;//工作时间
    private double workTimeT;//累计时间
    private double energyTonight;//本次用电量
    private double energyTotal;//累计用电量
    private double ledLux;//灯头照度值
    private double ppkToday;//当天负载峰值功率
    private double ppkHistory;//历史负载峰值功率
    private double energyToday;//当日用电
    private double saveMoneyT;//经济收益
    private double cO2T;//CO2减排量
    private double energyMonth;//月用电
    private double saveMoneyMonth;//当月经济收益
    private double cO2Month;//当月CO2排放量
    private double gridEnergyYear;//年电量
    private double saveMoneyYear;//当年经济收益
    private double cO2Year;//当年CO2减排量

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

    public double getCurrError() {
        return currError;
    }

    public void setCurrError(double currError) {
        this.currError = currError;
    }

    public double getGridVolt() {
        return gridVolt;
    }

    public void setGridVolt(double gridVolt) {
        this.gridVolt = gridVolt;
    }

    public double getGridCurr() {
        return gridCurr;
    }

    public void setGridCurr(double gridCurr) {
        this.gridCurr = gridCurr;
    }

    public double getGridFreq() {
        return gridFreq;
    }

    public void setGridFreq(double gridFreq) {
        this.gridFreq = gridFreq;
    }

    public double getGridPF() {
        return gridPF;
    }

    public void setGridPF(double gridPF) {
        this.gridPF = gridPF;
    }

    public double getGridAP() {
        return gridAP;
    }

    public void setGridAP(double gridAP) {
        this.gridAP = gridAP;
    }

    public double getGridRP() {
        return gridRP;
    }

    public void setGridRP(double gridRP) {
        this.gridRP = gridRP;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getLedDim() {
        return ledDim;
    }

    public void setLedDim(double ledDim) {
        this.ledDim = ledDim;
    }

    public double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(double workTime) {
        this.workTime = workTime;
    }

    public double getWorkTimeT() {
        return workTimeT;
    }

    public void setWorkTimeT(double workTimeT) {
        this.workTimeT = workTimeT;
    }

    public double getEnergyTonight() {
        return energyTonight;
    }

    public void setEnergyTonight(double energyTonight) {
        this.energyTonight = energyTonight;
    }

    public double getEnergyTotal() {
        return energyTotal;
    }

    public void setEnergyTotal(double energyTotal) {
        this.energyTotal = energyTotal;
    }

    public double getLedLux() {
        return ledLux;
    }

    public void setLedLux(double ledLux) {
        this.ledLux = ledLux;
    }

    public double getPpkToday() {
        return ppkToday;
    }

    public void setPpkToday(double ppkToday) {
        this.ppkToday = ppkToday;
    }

    public double getPpkHistory() {
        return ppkHistory;
    }

    public void setPpkHistory(double ppkHistory) {
        this.ppkHistory = ppkHistory;
    }

    public double getEnergyToday() {
        return energyToday;
    }

    public void setEnergyToday(double energyToday) {
        this.energyToday = energyToday;
    }

    public double getSaveMoneyT() {
        return saveMoneyT;
    }

    public void setSaveMoneyT(double saveMoneyT) {
        this.saveMoneyT = saveMoneyT;
    }

    public double getcO2T() {
        return cO2T;
    }

    public void setcO2T(double cO2T) {
        this.cO2T = cO2T;
    }

    public double getEnergyMonth() {
        return energyMonth;
    }

    public void setEnergyMonth(double energyMonth) {
        this.energyMonth = energyMonth;
    }

    public double getSaveMoneyMonth() {
        return saveMoneyMonth;
    }

    public void setSaveMoneyMonth(double saveMoneyMonth) {
        this.saveMoneyMonth = saveMoneyMonth;
    }

    public double getcO2Month() {
        return cO2Month;
    }

    public void setcO2Month(double cO2Month) {
        this.cO2Month = cO2Month;
    }

    public double getGridEnergyYear() {
        return gridEnergyYear;
    }

    public void setGridEnergyYear(double gridEnergyYear) {
        this.gridEnergyYear = gridEnergyYear;
    }

    public double getSaveMoneyYear() {
        return saveMoneyYear;
    }

    public void setSaveMoneyYear(double saveMoneyYear) {
        this.saveMoneyYear = saveMoneyYear;
    }

    public double getcO2Year() {
        return cO2Year;
    }

    public void setcO2Year(double cO2Year) {
        this.cO2Year = cO2Year;
    }

}
