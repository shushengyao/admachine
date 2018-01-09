package com.xmlan.machine.common.config;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.util.PropertiesUtils;
import com.xmlan.machine.common.util.StringUtils;

import java.util.Map;

/**
 * Created by Ayakura Yuki on 2017/7/11.
 */
public final class Global {

    /**
     * 当前对象实例
     */
    private static Global global = new Global();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesUtils loader = new PropertiesUtils("machine.properties");

    /**
     * session管理员标记
     */
    public static final String USER = "managementUser";

    /**
     * 极光推送Master Secret
     */
    public static String getMasterSecret() {
        String masterSecret = getConfig("masterSecret");
        if (StringUtils.isNotBlank(masterSecret)) {
            return masterSecret;
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 极光推送APP Key
     */
    public static String getAppKey() {
        String appKey = getConfig("appKey");
        if (StringUtils.isNotBlank(appKey)) {
            return appKey;
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 获取当前对象实例
     */
    public static Global getInstance() {
        return global;
    }

    /**
     * 获取管理员页面URL头
     *
     * @return
     */
    public static String getAdminPath() {
        return getConfig("adminPath");
    }

    /**
     * 获取单页最大条目数，如果配置不正确，默认返回10
     *
     * @return
     */
    public static int getPageSize() {
        String pageSizeConfig = getConfig("pageSize");
        if (StringUtils.isNumeric(pageSizeConfig)) {
            return Integer.parseInt(pageSizeConfig);
        } else {
            return 10;
        }
    }

    /**
     * 获取媒体路径
     *
     * @return
     */
    public static String getMediaPath() {
        String mediaPath = getConfig("mediaPath");
        if (StringUtils.isNotBlank(mediaPath)) {
            return mediaPath;
        } else {
            return "/media";
        }
    }

    public static String getImageTag() {
        String imageTag = getConfig("imageTag");
        if (StringUtils.isNoneBlank(imageTag)) {
            return imageTag;
        } else {
            return "images";
        }
    }

    public static String getVideoTag() {
        String videoTag = getConfig("videoTag");
        if (StringUtils.isNoneBlank(videoTag)) {
            return videoTag;
        } else {
            return "videos";
        }
    }

    /**
     * 获取配置
     *
     * @param key
     * @return
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = loader.getProperty(key);
            map.put(key, value != null ? value : "");
        }
        return value;
    }

    /**
     * 页面获取常量
     *
     * @param field
     * @return
     */
    public static Object getConst(String field) {
        try {
            return Global.class.getField(field).get(null);
        } catch (Exception ignore) { // 异常代表无配置，这里什么也不做
        }
        return null;
    }

}
