package com.xmlan.machine.common.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by Ayakura Yuki on 2017/7/10. <br/>
 * 唯一性ID生成工具
 */
public class IDUtils {

    private static SecureRandom random = new SecureRandom();

    /**
     * 生成唯一的UUID
     *
     * @return 无中划线的UUID字符串
     */
    public static String UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 生成随机Long
     *
     * @return
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return EncodeUtils.encodeBase62(randomBytes);
    }

}
