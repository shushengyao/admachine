package com.xmlan.machine.mobile.provider;

import com.xmlan.machine.common.base.BaseController;
import com.xmlan.machine.common.cache.AdvertisementMachineCache;
import com.xmlan.machine.common.task.LedTask;
import com.xmlan.machine.common.util.TokenUtils;
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine;
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor;
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService;
import com.xmlan.machine.module.advertisementMachine.service.MachineSensorService;
import com.xmlan.machine.module.led_machine.entity.Led_machine;
import com.xmlan.machine.module.led_machine.service.Led_machineService;
import com.xmlan.machine.module.xixun.controller.*;
import com.xmlan.machine.module.xixun.util.InvokeBuildInJsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 手机端 天气传感器数据服务接口
 *
 * Package: com.xmlan.machine.mobile.provider
 *
 * @author ayakurayuki
 * @date 2018/1/19-14:26
 */
@Controller
@RequestMapping("${mobilePath}/sensor")
public class SensorMobileServiceProvider extends BaseController {
    @Autowired
    AdvertisementMachineService service;
    @Autowired
    Led_machineService led_machineService;
    @Autowired
    MachineSensorService sensorService;

    /**
     * 通过广告机ID获取天气信息
     * <p>
     * URL: /mob/sensor/info
     * <p>
     * Method: Post
     *
     * @param machineID int 广告机ID
     * @param token     String token身份验证
     * @return 传感器天气信息对象，身份验证不通过则返回null
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public MachineSensor info(int machineID, String token) {
        if (!TokenUtils.validateToken(token)) {
            return null;
        }
        return AdvertisementMachineCache.getSensorInfo(machineID);
    }

    @RequestMapping(value = "/pushLED", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public HashMap pushLED(String led, String machineID, String inf, String authname){
//        int id = Integer.parseInt(ledID);
        int machineID_ =Integer.parseInt(machineID);
        HashMap<String,String> hashMap= new HashMap<>();
        MachineSensor sensorList = null;
        String info;
        String city = "珠海";
        if (machineID.equals("all")){
            List<MachineSensor> allList = new ArrayList<>();
            allList = sensorService.findAll();
            if (allList.size() != 0){
                for (int i = 0;i < allList.size();i++){
                    sensorList = allList.get(i);
                }
            }else {
                sensorList = null;
            }
        }else {
            sensorList = sensorService.getByMachineID(machineID_);
        }


        ScreenHeight height = new ScreenHeight();
        int screeHeight = Integer.parseInt(height.getScreenHeight(led));
        String size = String.valueOf(screeHeight/16);
        if (inf.equals("1")){
            info = inf;
        }else {
            Date date = new Date();
            String dataForm = new SimpleDateFormat("yyyy年MM月dd日").format(date);//
            String dataNow = new SimpleDateFormat("HH时mm分").format(date);
            Object unknownObject = null;
            AdvertisementMachine machine;

            machine = service.getAD(machineID_);
            if (machine.getCity()!=null || !machine.getCity().equals(unknownObject)){
                city = machine.getCity();
            }
            String temperature = "-";//温度
            String humidity = "-";//湿度
            String brightness = "-";//亮度
            String co2 = "-";//
            String cH2O = "-";
            String tVoc = "-";
            String pm25 = "-";
            String pm10 = "-";
            if (sensorList.getTemperature() != null){
                temperature = sensorList.getTemperature();
            }
            if (sensorList.getHumidity() != null){
                humidity = sensorList.getHumidity();
            }
            if (sensorList.getBrightness() != null){
                brightness = sensorList.getBrightness();
            }
            if (sensorList.geteCo2() != null){
                co2 = sensorList.geteCo2();
            }
            if (sensorList.geteCH2O() != null){
                cH2O = sensorList.geteCH2O();
            }
            if (sensorList.gettVoc() != null){
                tVoc = sensorList.gettVoc();
            }
            if (sensorList.getPm25() != null){
                pm25 = sensorList.getPm25();
            }
            if (sensorList.getPm10() != null){
                pm10 = sensorList.getPm10();
            }
            StringBuffer buffer = new StringBuffer();
            buffer.append("<p style=\"font-size:");
            buffer.append(size);
            buffer.append("px;line-height:20px\">");
            buffer.append(city);
            info = buffer.toString()+ "<br/>" +
                    dataForm + "<br/>" +
                    dataNow+"<br/>" +
                    "温度：" + temperature + "℃<br/>" +
                    "湿度：" + humidity +"<br/>" +
                    "亮度：" + brightness +"lux<br/>" +
                    "CO2："+ co2 +"ppm<br/>" +
                    "甲醛："+ cH2O +"mg/m³<br/>"+
                    "TVOC："+ tVoc +"mg/m³<br/>" +
                    "PM25：" + pm25 + "mg/m³<br/>"+
                    "PM10："+ pm10 +"</p>#030303" ;
        }
        if (push(led,info,authname)==true){
            hashMap.put("result","success");
        }else {
            hashMap.put("result","error");
        }
        return hashMap;
    }

    private boolean pushInfo(MachineSensor machineSensor){
//        boolean push =
        return false;
    }

    public boolean push(String led,String info,String authname) {
        Timer timer = new Timer();
        InvokeBuildInP inJs = new InvokeBuildInP();
        InvokeBuildInJsData inJsData = new InvokeBuildInJsData();
        inJsData.html = info;
        inJsData.type = "invokeBuildInJs"+led;
        boolean mp4 = false;
        String playList = led_machineService.selectPlayList(led);
        if (playList.substring(playList.lastIndexOf(".")).equals(".mp4")){
            mp4 = true;
        }
        try {
            inJs.invokeBuildInJs(inJsData);
            return true;
        }catch (Exception e){
            return false;
        }finally {
            timer.schedule(new Task(led,mp4,authname), 60000);
        }
    }
    class Task extends TimerTask {
        private String param;
        private boolean mp4;
        private String authname;
        public Task(String param,boolean mp4,String authname) {
            this.param = param;
            this.mp4 = mp4;
            this.authname = authname;
        }
        @Override
        public void run() {
            Clear clear = new Clear();
            clear.clea(param);
            if (mp4 != true){
                LoadUrl loadUrl =new  LoadUrl();
                loadUrl.loadUrl(param,authname);
            }
        }
    }
}
