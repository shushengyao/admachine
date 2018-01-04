package com.xmlan.machine.common.config;

import com.google.common.collect.Maps;
import com.xmlan.machine.common.util.PropertiesUtils;
import com.xmlan.machine.common.util.StringUtils;

import java.util.Map;

/**
 * Created by Ayakura Yuki on 2017/7/11.
 */
public class Global {

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

    public static String getApacheServer() {
        String apacheServer = getConfig("apacheServer");
        if (StringUtils.isNoneBlank(apacheServer)) {
            return apacheServer;
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 获取配置的图片存储路径
     *
     * @return 配置正确，返回配置的路径；错误，返回默认路径"/images"
     */
    public static String getImagePath() {
        String imagePath = getConfig("imagePath");
        if (StringUtils.isNoneBlank(imagePath)) {
            return imagePath;
        } else {
            return "/images";
        }
    }

    /**
     * 获取配置的视频存储路径
     *
     * @return 配置正确，返回配置的路径；错误，返回默认路径"/videos"
     */
    public static String getVideoPath() {
        String videoPath = getConfig("videoPath");
        if (StringUtils.isNoneBlank(videoPath)) {
            return videoPath;
        } else {
            return "/videos";
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
