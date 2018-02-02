package com.xmlan.machine.common.config;

import com.xmlan.machine.common.util.PropertiesUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by ayakurayuki on 2018/2/2-15:58. <br/>
 * Package: com.xmlan.machine.common.config <br/>
 */
public class TinyImage {

    private static final Logger logger = LogManager.getLogger(TinyImage.class);
    private Properties properties;
    private String path = "TinyImage.properties";
    private String tinyImageEnableKey = "tinyImage.enable";
    private String tinyImageActivateKey = "tinyImage.activate";

    private static class PythonHolder {
        private static final TinyImage instance = new TinyImage();
    }

    public static TinyImage getInstance() {
        return PythonHolder.instance;
    }

    private TinyImage() {
        PropertiesUtils utils = new PropertiesUtils(path);
        properties = utils.getProperties();
    }

    private void setOption(String key, boolean option) {
        try {
            File file = new DefaultResourceLoader().getResource(path).getFile(); // 通过默认资源文件获取
            OutputStream outputStream = new FileOutputStream(file); // 获取输出流
            properties.setProperty(key, Boolean.toString(option)); // 设置选项
            properties.store(outputStream, ""); // 输出到properties
            outputStream.close(); // 关闭输出流
            logger.info("Key " + key + " switch to " + Boolean.toString(option)); // log
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean valueOf(String key) {
        if (properties.containsKey(key)) {
            String value = properties.getProperty(key);
            return Boolean.valueOf(value);
        } else {
            return false;
        }
    }

    /**
     * 是否启动缩图功能
     *
     * @return 配置的值
     */
    public boolean isActivated() {
        return valueOf(tinyImageActivateKey);
    }

    /**
     * 设置是否启动缩图
     *
     * @param option 选项：true/false
     */
    public void setActivated(boolean option) {
        setOption(tinyImageActivateKey, option);
    }

    /**
     * 判断是否允许缩图功能
     *
     * @return 配置的值
     */
    public boolean isEnable() {
        return valueOf(tinyImageEnableKey);
    }

    /**
     * 设置是否允许缩图
     *
     * @param option 选项：true/false
     */
    public void setEnable(boolean option) {
        setOption(tinyImageEnableKey, option);
    }

}
