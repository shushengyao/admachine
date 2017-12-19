package com.xmlan.machine.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmlan.machine.common.config.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ayakura Yuki on 2017/7/20. <br />
 * 获取request，将文件存储到Apache文件服务器。
 */
public class ImageUploadUtils {

    private static final Logger logger = LogManager.getLogger(ImageUploadUtils.class);

    public static final String IMAGE_KEY = "images";

    private static boolean isImage(String extensions) {
        return extensions.endsWith(".png")
                || extensions.endsWith(".jpg")
                || extensions.endsWith(".jpeg")
                || extensions.endsWith(".bmp")
                || extensions.endsWith("gif")
                || extensions.endsWith(".tif")
                || extensions.endsWith(".tiff");
    }

    public static String UploadImages(HttpServletRequest request) {
        Map<String, List<String>> map = Maps.newHashMap();
        List<String> fileList = Lists.newArrayList();
        // 将上下文初始化
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 检查form中是否带有 enctype="multipart/form-data"
        if (resolver.isMultipart(request)) {
            // 转化request为多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获取multiRequest的全部文件名
            Iterator iterator = multiRequest.getFileNames();
            // 图片顺序计数器
            int i = 0;
            while (iterator.hasNext()) {
                // 遍历所有文件
                MultipartFile file = multiRequest.getFile(iterator.next().toString());
                try {
                    if (file != null
                            && isImage(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')))) {
                        // 日期 + 后缀名
                        String filename = DateUtils.GetDate("yyyy.MM.dd.HH.mm.ss_") + i
                                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                        // 存储文件
                        try {
                            file.transferTo(new File(Global.getImagePath() + "/" + filename));
                        } catch (IOException e) {
                            logger.debug(e);
                        }
                        fileList.add(Global.getApacheServer() + "/" + filename);
                        // 压缩图片
                        // try {
                        //     String[] args = new String[]{"python3", "tinyimages.py", Global.getImagePath() + "/" + filename};
                        //     Process process = Runtime.getRuntime().exec(args);
                        //     BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        //     String line;
                        //     while ((line = in.readLine()) != null) {
                        //         System.out.println(line);
                        //     }
                        //     in.close();
                        // } catch (IOException e) {
                        //     e.printStackTrace();
                        // }
                        logger.trace("Added image: " + filename);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    logger.error("没有选中文件！");
                }
            }
        }
        map.put(IMAGE_KEY, fileList);
        logger.trace("map size: " + map.size() + "\tfileList size: " + fileList.size());
        return fileList.isEmpty() ? "[]" : JsonUtils.toJsonString(map);
    }

}
