package com.xmlan.machine.module.xixun.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 加载xwalk网页
 * @program: admachine
 * @description: util
 * @author: YSS
 * @create: 2018-07-25 10:59
 **/
public class CallXwalkFnData {
    public String type = "callXwalkFn";
    public String fn = "loadUrl";

    public class arg1{
        public  String url = "http://192.168.0.218:8081/test002.html";
        public String backupUrl = "file:///mnt/sdcard/res/backup.html";
        public boolean persistent = true;
    }
    arg1 arg = new arg1();
    public arg1 getArg(){
        return arg;
    }
}
