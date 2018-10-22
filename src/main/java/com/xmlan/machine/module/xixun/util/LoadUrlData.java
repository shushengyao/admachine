package com.xmlan.machine.module.xixun.util;

/**
 * @program: admachine
 * @description: util
 * @author: YSS
 * @create: 2018-07-24 17:25
 **/
public class LoadUrlData {
    public String type="loadUrl";
    public String url;//url:'file:///mnt/sdcard/test.html, //也可以是本地路径
    public boolean persistent = true ;//持久化，重启会自动加载url

}
