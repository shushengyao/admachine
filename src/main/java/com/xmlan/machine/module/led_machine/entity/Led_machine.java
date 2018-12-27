package com.xmlan.machine.module.led_machine.entity;

import java.io.Serializable;

/**
 * led广告屏实体类
 * @program: admachine
 * @description: entity
 * @author: YSS
 * @create: 2018-07-25 18:11
 **/
public class Led_machine implements Serializable {
    //广告id
    private int id;
    //广告名字
    private String name;
    //led编号
    private String led;
    //所属广告机id
    private int machine_id;
    //所属用户id
    private int user_id;
    //播放列表
    private String play_list;


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

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPlay_list() {
        return play_list;
    }

    public void setPlay_list(String play_list) {
        this.play_list = play_list;
    }
}
