package com.xmlan.machine.common.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;



public class WorkTimer extends HttpServlet{
    //时间间隔(一天)
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    public WorkTimer(){
        ApplicationContext context = ApplicationContextUtil.getApplicationContext();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10); //夜晚23点
        calendar.set(Calendar.MINUTE, 24);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); //第一次执行定时任务的时间
        String[] s = context.getBeanDefinitionNames();
        System.err.print("s="+s);
        //这里通过applicationContext的getbean方法拿到实现类，实现bean调用
        TimerTask task =context.getBean(NFDFlightDataTimerTask.class);
//        NFDFlightDataTimerTask tasks = new NFDFlightDataTimerTask();

        Timer timer = new Timer();
        timer.schedule(task,date, PERIOD_DAY);
    }
}