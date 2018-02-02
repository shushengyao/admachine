package com.xmlan.machine.test;

import java.util.regex.Pattern;

/**
 * Created by ayakurayuki on 2018/1/17-18:17. <br/>
 * Package: com.xmlan.machine.test <br/>
 */
public class RegexPlayground {

    private static final int NO_MEDIA = 100000;
    private static final int VIDEO = 100001;
    private static final int IMAGE = 100002;

    private static int mediaType(String extension) {
        if (isVideo(extension)) {
            return VIDEO;
        } else if (isImage(extension)) {
            return IMAGE;
        } else {
            return NO_MEDIA;
        }
    }

    private static boolean isVideo(String extension) {
        return Pattern.matches("[\\s\\S]*\\.(mp4|wmv|avi|flv|3gp|mkv)", extension);
    }

    static boolean isImage(String extension) {
        return Pattern.matches("[\\s\\S]*\\.(png|jpg|jpeg|bmp|git|tif|tiff)", extension);
    }

    public static void main(String[] args) {
        System.out.println(mediaType("images/172.png"));
    }

}
