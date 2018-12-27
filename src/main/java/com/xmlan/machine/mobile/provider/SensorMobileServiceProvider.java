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
import com.xmlan.machine.module.xixun.controller.Clear;
import com.xmlan.machine.module.xixun.controller.InvokeBuildInJs;
import com.xmlan.machine.module.xixun.controller.LoadUrl;
import com.xmlan.machine.module.xixun.controller.ScreenHeight;
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
        MachineSensor sensorList;
        String info;
        String city = "珠海";
        sensorList = sensorService.getByMachineID(machineID_);
        ScreenHeight height = new ScreenHeight();
        int screeHeight = Integer.parseInt(height.getScreenHeight(led));
        String size = String.valueOf(screeHeight/12);
        if (inf.equals("1")){
            info = inf;
        }else {
            Date date = new Date();
            String dataForm = new SimpleDateFormat("yyyy年MM月dd日HH时mm分").format(date);
            Object unknownObject = null;
            AdvertisementMachine machine;

            machine = service.getAD(machineID_);
            if (machine.getCity()!=null || !machine.getCity().equals(unknownObject)){
                city = machine.getCity();
            }
            info = "<i style=\"font-size:"+size+"px\">地点"+city + "<br/>时间：" + dataForm + "<br/>温度：" + sensorList.getTemperature() + "℃&nbsp;&nbsp;湿度：" + sensorList.getHumidity() + "%RH<br/>环境亮度：" + sensorList.getBrightness() + "cd/m²<br/>空气污染指数：" + sensorList.getPm25() + "μg/m³</i>#030303";
        }
        if (push(led,info,authname)==true){
            hashMap.put("result","success");
        }else {
            hashMap.put("result","error");
        }
        return hashMap;
    }

    public boolean push(String led,String info,String authname) {
        Timer timer = new Timer();
        InvokeBuildInJs inJs = new InvokeBuildInJs();
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
