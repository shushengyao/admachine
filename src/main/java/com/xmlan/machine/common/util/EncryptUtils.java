package com.xmlan.machine.common.util;

import com.xmlan.machine.common.base.AlgorithmEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ayakurayuki
 * @date 2017/12/11-13:57
 * Package: com.xmlan.machine.common.util
 */
public class EncryptUtils {

    private final static Logger logger = LogManager.getLogger(EncryptUtils.class);
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
            logger.error("Type " + type.name() + " is not support.");
            return null;
        }
    }

    /**
     * @param raw : content
     * @return encrypt password
     */
    private static String ConvertToString(byte[] raw) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : raw) {
            stringBuilder.append(HEX[(b >>> 4) & 0x0f]);
            stringBuilder.append(HEX[b & 0x0f]);
        }
        return stringBuilder.toString().toLowerCase();
    }

    /**
     * @param message : content
     * @param type    : algorithm type
     * @return encrypted content
     */
    private static String TenTimes(String message, AlgorithmEnum type) {
        String result = message;
        final int times = 10;
        for (int i = 0; i < times; i++) {
            result = ConvertToString(GetEncodeByte(result, type));
        }
        return result;
    }

    /**
     * 10 times loop encrypt by md5.
     *
     * @param message : content
     * @return encrypted content
     */
    public static String MD5ForTenTimes(String message) {
        return TenTimes(message, AlgorithmEnum.MD5);
    }

    /**
     * encrypt by md5.
     *
     * @param message : content
     * @return encrypted content
     */
    public static String MD5(String message) {
        return ConvertToString(GetEncodeByte(message, AlgorithmEnum.MD5));
    }

    /**
     * 10 times loop encrypt by sha-1.
     *
     * @param message : content
     * @return encrypted content
     */
    public static String SHA1ForTenTimes(String message) {
        return TenTimes(message, AlgorithmEnum.SHA1);
    }

    /**
     * encrypt by sha-1.
     *
     * @param message : content
     * @return encrypted content
     */
    public static String SHA1(String message) {
        return ConvertToString(GetEncodeByte(message, AlgorithmEnum.SHA1));
    }

    /**
     * 10 times loop encrypt by sha-256.
     *
     * @param message : content
     * @return encrypted content
     */
    public static String SHA256ForTenTimes(String message) {
        return TenTimes(message, AlgorithmEnum.SHA256);
    }

    /**
     * encrypt by sha-256.
     *
     * @param message : content
     * @return encrypted content
     */
    public static String SHA256(String message) {
        return ConvertToString(GetEncodeByte(message, AlgorithmEnum.SHA256));
    }

}
