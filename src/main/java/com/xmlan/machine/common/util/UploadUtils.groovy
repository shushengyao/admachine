package com.xmlan.machine.common.util

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.xmlan.machine.common.config.Global
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartResolver

import javax.servlet.http.HttpServletRequest

/**
 * Created by Ayakura Yuki on 2017/7/20. <br />
 * 获取request，将文件存储到Apache文件服务器。
 */
class UploadUtils {

    private static final Logger logger = LogManager.getLogger(UploadUtils.class)

    public static final String MEDIA_KEY = "media"

    static String uploadImages(HttpServletRequest request) {
        def imagePath = new File("${Global.mediaPath}/${Global.imageTag}".toString())
        if (!imagePath.exists()) imagePath.mkdirs()
        def videoPath = new File("${Global.mediaPath}/${Global.videoTag}".toString())
        if (!videoPath.exists()) videoPath.mkdirs()

        Map<String, List<String>> json = Maps.newHashMap()
        List<String> fileList = Lists.newArrayList()
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.session.servletContext)
        if (resolver.isMultipart(request)) {
            // 检查form中是否带有 enctype="multipart/form-data"
            // 转化normal-request为multipart-request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request
            Iterator iterator = multiRequest.fileNames
            // 获取multiRequest的全部文件名
            int i = 1
            // 编号计数器
            while (iterator.hasNext()) {
                // 遍历所有文件
                MultipartFile file = multiRequest.getFile(iterator.next().toString())
                try {
                    String extension = file.originalFilename.substring(file.originalFilename.lastIndexOf('.'))
                    if (file != null && isMedia(file.originalFilename)) {
                        String filename = "${DateUtils.GetDate('yyyy-MM-dd_HH-mm-ss')}_No${i}${extension}"
                        try {
                            // 存储文件
                            if (isImage(extension)) {
                                file.transferTo(new File("${Global.mediaPath}/${Global.imageTag}/${filename}"))
                                fileList.add "${Global.imageTag}/${filename}".toString()
                            } else if (isVideo(extension)) {
                                file.transferTo(new File("${Global.mediaPath}/${Global.videoTag}/${filename}"))
                                fileList.add "${Global.videoTag}/${filename}".toString()
                            }
                        } catch (IOException e) {
                            logger.debug e
                        }
                        i++
                        logger.trace "Added media: ${filename}"
                    }
                } catch (StringIndexOutOfBoundsException ignored) {
                    logger.info "没有选中文件！"
                }
            }
        }
        json.put MEDIA_KEY, fileList
        logger.trace "map size: ${json.size()}\tfileList size: ${fileList.size()}"
        return fileList.isEmpty() ? "[]" : JsonUtils.toJsonString(json)
    }

    private static boolean isMedia(String extension) {
        return extension ==~ /[\s\S]*\.(png|jpg|jpeg|bmp|git|tif|tiff|mp4|wmv|avi|flv|3gp|mkv)/
    }

    private static boolean isVideo(String extension) {
        return extension ==~ /[\s\S]*\.(mp4|wmv|avi|flv|3gp|mkv)/
    }

    private static boolean isImage(String extension) {
        return extension ==~ /[\s\S]*\.(png|jpg|jpeg|bmp|git|tif|tiff)/
    }

    private static void tinyImage(String filename) {
        try {
            String[] args = ["python3", "tinyimages.py", "${Global.mediaPath}/${Global.imageTag}/${filename}"] as String[]
            def process = Runtime.runtime.exec args
            process.inputStream.eachLine { line ->
                println(line)
            }
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

}
