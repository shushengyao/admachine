package com.xmlan.machine.common.config;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.util.PropertiesUtils;
import com.xmlan.machine.common.util.StringUtils;

import java.util.Map;

/**
 * 全局配置类
 * <p>
 * Created by Ayakura Yuki on 2017/7/11.
 */
public final class Global {

    /**
     * session管理员标记
     */
    public static final String USER = "managementUser";
    /**
     * 表单验证Session标记
     */
    public static final String FORM_TOKEN = "FORM_TOKEN";

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
     * @return 单页最大条目数，或默认值10
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
     * @return 配置的路径，如未配置则返回默认路径
     */
    public static String getMediaPath() {
        String mediaPath = getConfig("mediaPath");
        if (StringUtils.isNotBlank(mediaPath)) {
            return mediaPath;
        } else {
            return "/media";
        }
    }

    /**
     * 获取图片标记
     *
     * @return 配置的标记，如未配置则返回默认标记
     */
    public static String getImageTag() {
        String imageTag = getConfig("imageTag");
        if (StringUtils.isNoneBlank(imageTag)) {
            return imageTag;
        } else {
            return "images";
        }
    }

    /**
     * 获取视频标记
     *
     * @return 配置的标记，如未配置则返回默认标记
     */
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
     * @param key 键
     * @return 对应的值或空字符串
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
     * @param field 键
     * @return 常量或null
     */
    public static Object getConst(String field) {
        try {
            return Global.class.getField(field).get(null);
        } catch (Exception ignore) {
        }
        return null;
    }

}
