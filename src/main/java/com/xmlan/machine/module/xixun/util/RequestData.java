package com.xmlan.machine.module.xixun.util;

/**
 * @program: admachine
 * @description: util
 * @author: YSS
 * @create: 2018-07-24 13:19
 **/
public class RequestData {
    public String type="invokeBuildInJs";
    public String method ="scrollMarquee";
    public String scrollBy ="count";
    public int num =2; //if scrollBy is 'count', scroll twice
    public String html ="I have worn <b>glasses</b> since early childhood.";
    public String direction ="left";
    public String align ="bottom";
    public int interval =40; //ms
    public int step =2; //px
    public String clearType = "clear";
    public String invokeJs = "invokeJs";
    public String loadUrl = "loadUrl";
}
