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
    public Number num = 5;// 滚动5次, 注意类型为Number, 填0停止滚动，填负数永久滚动
    public String html = "<head>\n" +
            "    <style type=\"text/css\">\n" +
            "         body{background: #000000}" +
            "    </style>\n" +
            "</head> <i><b style=\\\"color:blue; \\\">珠海</b>希梦蓝科技有限公司</i>";
    public Number interval = 50;//步进间隔，单位毫秒，注意类型为Number
    public Number step = 1;//步进距离，单位像素，注意类型为Number
    public String direction = "left";//往左滚动，可填值left、 right
    public String align = "top";//在上方显示，可填值top、center、bottom
}
