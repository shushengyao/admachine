package com.xmlan.machine.module.xixun.util;

/**
 * 背景及滚动文字信息
 * @program: admachine
 * @description: util
 * @author: YSS
 * @create: 2018-07-25 10:37
 **/
public class InvokeBuildInJsData {
    public String type = "invokeBuildInJs";//method   scrollMarquee
    public String method = "scrollMarquee";
    public Number num = -1;// 滚动5次, 注意类型为Number, 填0停止滚动，填负数永久滚动
    public String html = "";
    public Number interval = 10;//步进间隔，单位毫秒，注意类型为Number
    public Number step = 1;//步进距离，单位像素，注意类型为Number
    public String direction = "left";//往左滚动，可填值left、 right
    public String align = "center";//在上方显示，可填值top、center、bottom
}
