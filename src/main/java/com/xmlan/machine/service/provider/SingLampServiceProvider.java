package com.xmlan.machine.service.provider;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.util.DateUtils;
import com.xmlan.machine.common.util.StringUtils;
import com.xmlan.machine.module.singLamp.entity.SingLamp;
import com.xmlan.machine.module.singLamp.service.SingLampService;
import com.xmlan.machine.module.singlelamp_new.entity.SingLampNew;
import com.xmlan.machine.module.singlelamp_new.service.SingLampNewService;
import com.xmlan.machine.module.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @program: admachine
 * @description: 灯杆设备单灯控制器端数据
 * @author: YSS
 * @create: 2018-11-07 17:13
 **/
@Controller
@RequestMapping("${servicePath}/singLamp")
public class SingLampServiceProvider extends BaseController {
    @Autowired
    private SingLampService singLampService;

    @Autowired
    private final SingLampNewService singLampNewService;
    private final SysLogService sysLogService;
    private final ThreadPoolTaskExecutor taskExecutor;

    public SingLampServiceProvider(SysLogService sysLogService, ThreadPoolTaskExecutor taskExecutor,SingLampNewService singLampNewService) {
        this.sysLogService = sysLogService;
        this.taskExecutor = taskExecutor;
        this.singLampNewService = singLampNewService;
    }

    /**
     * 保存/更新单灯控制器数据
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    HashMap<String, Object> save(int id,int machineID,double currError,double gridVolt,double gridCurr,double gridFreq,double gridPF,double gridAP,double gridRP,
                                 double temperature,double ledDim,double workTime,double workTimeT,double energyTonight,double energyTotal,double ledLux,
                                 double ppkToday,double ppkHistory, double energyToday,double saveMoneyT,double cO2T,double energyMonth,double saveMoneyMonth,
                                 double cO2Month,double gridEnergyYear,double saveMoneyYear,double cO2Year){
        SingLamp singLamp = new SingLamp();
        singLamp.setId(id);
        singLamp.setMachineID(machineID);
        singLamp.setUpdateTime(DateUtils.getDateTime());
        singLamp.setCurrError(currError);
        singLamp.setGridVolt(gridVolt);
        singLamp.setGridCurr(gridCurr);
        singLamp.setGridFreq(gridFreq);
        singLamp.setGridPF(gridPF);
        singLamp.setGridAP(gridAP);
        singLamp.setGridRP(gridRP);
        singLamp.setTemperature(temperature);
        singLamp.setLedDim(ledDim);
        singLamp.setWorkTime(workTime);
        singLamp.setWorkTimeT(workTimeT);
        singLamp.setEnergyTonight(energyTonight);
        singLamp.setEnergyTotal(energyTotal);
        singLamp.setLedLux(ledLux);
        singLamp.setPpkToday(ppkToday);
        singLamp.setPpkHistory(ppkHistory);
        singLamp.setEnergyToday(energyToday);
        singLamp.setSaveMoneyT(saveMoneyT);
        singLamp.setcO2T(cO2T);
        singLamp.setEnergyMonth(energyMonth);
        singLamp.setSaveMoneyMonth(saveMoneyMonth);
        singLamp.setcO2Month(cO2Month);
        singLamp.setGridEnergyYear(gridEnergyYear);
        singLamp.setSaveMoneyYear(saveMoneyYear);
        singLamp.setcO2Year(cO2Year);
        System.err.print(JSONObject.toJSONString(singLamp));
        HashMap<String, Object> map = Maps.newHashMap();
        if (id == NEW_ID){
            singLampService.insertSingLamp(singLamp);
            id = singLamp.getId();
            map.put(keyResponseCode, DONE);
            map.put(keyMessage, "Insert!");
            map.put("id",id);
        }else {
//            singLampService.update(singLamp);
            map.put(keyResponseCode, DONE);
            map.put(keyMessage, "Updated!");
        }
        return map;
    }

    /**
     * 保存/更新单灯控制器数据
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/newSave", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    HashMap<String, Object> newSave(int id,int machineID,double deviceCode,double inputVoltage,double inputCurrent,double outputVol,double outputCurr,double gridAP,double gridAPD,
                                 double temperature,double ledBright,String powerStatus){
        SingLampNew singLamp = new SingLampNew();
        singLamp.setId(id);
        singLamp.setMachineID(machineID);
        singLamp.setUpdateTime(DateUtils.getDateTime());
        singLamp.setTemperature(temperature);
        singLamp.setDeviceCode(deviceCode);
        singLamp.setTemperature(temperature);
        singLamp.setInputVoltage(inputVoltage);
        singLamp.setInputCurrent(inputCurrent);
        singLamp.setOutputVol(outputVol);
        singLamp.setOutputCurr(outputCurr);
        singLamp.setGridAP(gridAP);
        singLamp.setGridAPD(gridAPD);
        singLamp.setPowerStatus(powerStatus);
        System.err.print(JSONObject.toJSONString(singLamp));
        HashMap<String, Object> map = Maps.newHashMap();
        if (id == NEW_ID){
            singLampNewService.insertSingLamp(singLamp);
            id = singLamp.getId();
            map.put(keyResponseCode, DONE);
            map.put(keyMessage, "Insert!");
            map.put("id",id);
        }else {
            map.put(keyResponseCode, DONE);
            map.put(keyMessage, "Updated!");
        }
        return map;
    }
}
