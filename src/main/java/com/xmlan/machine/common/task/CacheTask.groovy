package com.xmlan.machine.common.task

import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.cache.AdvertisementMachineCache
import com.xmlan.machine.common.cache.LedCache
import com.xmlan.machine.common.cache.MachineGroupCache
import com.xmlan.machine.common.cache.RoleCache
import com.xmlan.machine.common.cache.UserCache
import com.xmlan.machine.common.util.CacheUtils
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import com.xmlan.machine.module.advertisementMachine.entity.MachineSensor
import com.xmlan.machine.module.advertisementMachine.service.AdvertisementMachineService
import com.xmlan.machine.module.advertisementMachine.service.MachineSensorService
import com.xmlan.machine.module.led_machine.entity.Led_machine
import com.xmlan.machine.module.led_machine.service.Led_machineService
import com.xmlan.machine.module.machineGroup.entity.MachineGroup
import com.xmlan.machine.module.machineGroup.service.MachineGroupService
import com.xmlan.machine.module.xixun.controller.Clear
import com.xmlan.machine.module.xixun.controller.InvokeBuildInJs
import com.xmlan.machine.module.xixun.util.InvokeBuildInJsData
import net.sf.json.JSONObject
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.ui.Model
import org.springframework.ui.ModelMap

import java.text.SimpleDateFormat

/**
 * 缓存刷新调度任务和定时器任务 <br/>
 * YSS
 *
 */
@Component
class CacheTask {
    @Autowired
    AdvertisementMachineService service;
    @Autowired
    Led_machineService led_machineService
    @Autowired
    MachineSensorService sensorService

    private Logger logger = LogManager.getLogger(CacheTask.class)

    /**
     * 定时刷新缓存, 周期为30分钟
     */
    @Scheduled(fixedRate = 1800000L)
    void refreshCache() {
        CacheUtils.cacheManager.clearAll()
        AdvertisementMachineCache.initialCacheMap()
        AdvertisementCache.initialCacheMap()
        LedCache.initialCacheMap()
//        MachineGroupCache.initialCacheMap()
        UserCache.initialCacheMap()
//        RoleCache.initialCacheMap()
        logger.info "缓存刷新"
    }

    /**
     * 远程调用萤石云远程api获取accessToken，每6天执行1次，并把获取的值存入数据库
     */
    //每分钟一次0 0/1 * * * ?
    //0 0 0 21/6 * ?   21号开始，每6天执行一次
//    @Scheduled(cron = " 0 0 0 21/6 * ?")
//    void getToken() {
//        try {
//            Map<String, String> parms =new HashMap<>();
//            parms.put("appKey","51a534ebadf54c31a0848dc575dfa206");
//            parms.put("appSecret","8c32c67a73c87b9e461b2e3bdf58967a");
//            String post = HttpTools.httpRequestToString("https://open.ys7.com/api/lapp/token/get","post",parms);
//            System.err.print("post="+post);
//            JSONObject jsonObj= JSONObject.fromObject(post);
//            String value= jsonObj.getString("accessToken");
//            accessToken(value);
////            accessToken("123456");
////            System.out.println("执行当前时间"+formatter.format(Calendar.getInstance().getTime()));
//        } catch (Exception e) {
//            System.out.println("-------------定时器执行发生异常--------------");
//        }
//    }

    /**
     * 更新数据库accessToken字段值
     * @param accessToken
     */
//    void accessToken (String accessToken){
//        service.updateAccessToken(accessToken);
//    }
}