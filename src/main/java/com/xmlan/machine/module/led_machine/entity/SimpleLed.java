package com.xmlan.machine.module.led_machine.entity;

/**
 * @program: admachine
 * @description: entity
 * @author: YSS
 * @create: 2018-12-04 10:20
 **/
public class SimpleLed {
    //广告id
    private int id;
    //广告名字
    private String name;
    //led编号
    private String led;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }
}
