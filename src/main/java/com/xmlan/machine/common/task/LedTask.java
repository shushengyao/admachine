package com.xmlan.machine.common.task;

import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.advertisementMachine.service.MachineSensorService;
import com.xmlan.machine.module.led_machine.entity.Led_machine;
import com.xmlan.machine.module.led_machine.service.Led_machineService;
import com.xmlan.machine.module.xixun.controller.Clear;
import com.xmlan.machine.module.xixun.controller.InvokeBuildInJs;
import com.xmlan.machine.module.xixun.util.InvokeBuildInJsData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @program: admachine
 * @description: task
 * @author: YSS
 * @create: 2018-12-07 14:08
 **/
@Component
public class LedTask {
    @Autowired
    AdvertisementMachineService service;
    @Autowired
    Led_machineService led_machineService;
    @Autowired
    MachineSensorService sensorService;

    private Logger logger = LogManager.getLogger(CacheTask.class);

//    @Scheduled(fixedRate = 60000L)
    void pushLED() {
        List<Led_machine> list = led_machineService.findAll();
        int listSize = list.size();
        MachineSensor sensorList;
        String info = "暂无数据";
        String city = "珠海";
        Date date = new Date();
        String dataForm = new SimpleDateFormat("yyyy年MM月dd日HH时mm分").format(date);
        Object unknownObject = null;
        for (int i=0;i < listSize;i++){
            AdvertisementMachine machine = new AdvertisementMachine();
            machine = service.getAD(list.get(i).getMachine_id());
            if (machine.getCity()!=null || !machine.getCity().equals(unknownObject)){
                city = machine.getCity();
            }
            sensorList = sensorService.getByMachineID(list.get(i).getMachine_id());
            info = city+"时间："+dataForm+"-----实时温度："+sensorList.getTemperature()+"℃—空气湿度："+sensorList.getHumidity()+"%RH—环境亮度："+sensorList.getBrightness()+"cd/m²—空气污染指数："+sensorList.getPm25()+"μg/m³#030303";
//            System.err.print(info);
//            controLed(list.get(i).led,info)
//            LedTask.main("y10-a18-00006",info);
            LedTask.main(new String[]{"y10-a18-00006",info});
        }
        logger.info( "led广告屏幕推送天气");
    }

    public static void main(String[] args) {
        String[] base =args;
        String led = base[0];
        String info = base[1];
        Timer timer = new Timer();
        InvokeBuildInJs inJs = new InvokeBuildInJs();
        InvokeBuildInJsData inJsData = new InvokeBuildInJsData();
        inJsData.html = info;
        inJsData.type = "invokeBuildInJs"+led;
        inJs.invokeBuildInJs(inJsData);
        timer.schedule(new LedTask().new Task(led), 25000);
    }
    class Task extends TimerTask {
        private String param;
        public Task(String param) {
            this.param = param;
        }
        @Override
        public void run() {
            Clear clear = new Clear();
            clear.clea(param);
        }
    }
}
