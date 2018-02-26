package com.xmlan.machine.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ayakurayuki on 2018/2/2-16:20. <br/>
 * Package: com.xmlan.machine.test <br/>
 */
public class StringPlaygroundJava {

    @Test
    public void test() {
        // System.out.println(true + "");
        // System.out.println(Boolean.TRUE);

        String line = "ac-de-12-34-19-8f";
        // String pattern = "(([+])?(86)(-)?)?1[3|4|5|7|8]\\d{9}";
        String pattern = "([A-Fa-f0-9]{2}[:-]){5}[A-Fa-f0-9]{2}";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find()) {
            for (int i = 0; m.find(i); i++) {
                System.out.println("Found: " + m.group(i));
            }
        } else {
            System.out.println("NO MATCH");
        }
    }

}
