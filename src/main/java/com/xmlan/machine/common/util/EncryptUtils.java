package com.xmlan.machine.common.util;

import com.xmlan.machine.common.base.AlgorithmEnum;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ayakurayuki on 2017/12/11-13:57.
 * Package: com.xmlan.machine.common.util
 */
public class EncryptUtils {

    private final static char[] HEX = "0123456789ABCDEF".toCharArray();

    /**
     * @param message : content
     * @return byte[] : raw content
     */
    private static byte[] GetEncodeByte(String message, AlgorithmEnum type) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(type.getType());
            messageDigest.update(message.getBytes());
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Type " + type.name() + " is not support.");
            return null;
        }
    }

    /**
     * @param raw : content
     * @return encrypt password
     */
    private static String ConvertToString(byte[] raw) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : raw) {
            stringBuffer.append(HEX[(b >>> 4) & 0x0f]);
            stringBuffer.append(HEX[b & 0x0f]);
        }
        return stringBuffer.toString().toLowerCase();
    }

    /**
     * 10 times loop encrypt by md5.
     *
     * @param message
     * @return
     */
    public static String MD5ForTenTimes(String message) {
        String result = message;
        for (int i = 0; i < 10; i++)
            result = ConvertToString(GetEncodeByte(result, AlgorithmEnum.MD5));
        return result;
    }

    /**
     * encrypt by md5.
     *
     * @param message
     * @return
     */
    public static String MD5(String message) {
        return ConvertToString(GetEncodeByte(message, AlgorithmEnum.MD5));
    }

    /**
     * 10 times loop encrypt by sha-1.
     *
     * @param message
     * @return
     */
    public static String SHA1ForTenTimes(String message) {
        String result = message;
        for (int i = 0; i < 10; i++)
            result = ConvertToString(GetEncodeByte(result, AlgorithmEnum.SHA1));
        return result;
    }

    /**
     * encrypt by sha-1.
     *
     * @param message
     * @return
     */
    public static String SHA1(String message) {
        return ConvertToString(GetEncodeByte(message, AlgorithmEnum.SHA1));
    }

    /**
     * 10 times loop encrypt by sha-256.
     *
     * @param message
     * @return
     */
    public static String SHA256ForTenTimes(String message) {
        String result = message;
        for (int i = 0; i < 10; i++)
            result = ConvertToString(GetEncodeByte(result, AlgorithmEnum.SHA256));
        return result;
    }

    /**
     * encrypt by sha-256.
     *
     * @param message
     * @return
     */
    public static String SHA256(String message) {
        return ConvertToString(GetEncodeByte(message, AlgorithmEnum.SHA256));
    }

}
