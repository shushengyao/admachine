package com.xmlan.machine.module.xixun.controller;

import com.xmlan.machine.common.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @program: admachine
 * @description: test
 * @author: YSS
 * @create: 2018-08-07 11:42
 **/
public class GetFileName {

    public static void main(String[] args)
    {
        String [] fileName = FileUtils.getFileName("D:/RealtimeServer/public");
        for(String name:fileName)
        {
            System.out.println(name);
        }
    }
}
