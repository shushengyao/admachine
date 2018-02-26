package com.xmlan.machine.common.util

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.xmlan.machine.common.config.Global
import com.xmlan.machine.common.config.TinyImage
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
final class UploadUtils {

    private static final Logger logger = LogManager.getLogger(UploadUtils.class)

    public static final String MEDIA_KEY = "media"

    static String uploadImages(Object request) {
        def imagePath = new File("${Global.mediaPath}/${Global.imageTag}".toString())
        if (!imagePath.exists()) imagePath.mkdirs()
        def videoPath = new File("${Global.mediaPath}/${Global.videoTag}".toString())
        if (!videoPath.exists()) videoPath.mkdirs()

        Map<String, List<String>> json = Maps.newHashMap()
        List<String> fileList = Lists.newArrayList()
        if (request instanceof HttpServletRequest) {
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
                            String filename = "${DateUtils.getDate('yyyy-MM-dd_HH-mm-ss')}_No${i}${extension}"
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
        }
        if (request instanceof MultipartFile) {
            try {
                String extension = request.originalFilename.substring(request.originalFilename.lastIndexOf('.'))
                if (request != null && isMedia(request.originalFilename)) {
                    String filename = "${DateUtils.getDate('yyyy-MM-dd_HH-mm-ss_SSS')}_${extension}"
                    try {
                        // 存储文件
                        if (isImage(extension)) {
                            request.transferTo(new File("${Global.mediaPath}/${Global.imageTag}/${filename}"))
                            fileList.add "${Global.imageTag}/${filename}".toString()
                            if (TinyImage.instance.isActivated()) {
                                tinyImage filename
                            } else {
                                logger.info "当前未启动缩图功能，将原图保存"
                            }
                        } else if (isVideo(extension)) {
                            request.transferTo(new File("${Global.mediaPath}/${Global.videoTag}/${filename}"))
                            fileList.add "${Global.videoTag}/${filename}".toString()
                        }
                    } catch (IOException e) {
                        logger.debug e
                    }
                    logger.trace "Added media: ${filename}"
                }
            } catch (StringIndexOutOfBoundsException ignored) {
                logger.info "没有选中文件！"
            }
        }
        json.put MEDIA_KEY, fileList
        logger.trace "map size: ${json.size()}\tfileList size: ${fileList.size()}"
        return fileList.isEmpty() ? "[]" : JsonUtils.toJsonString(json)
    }

    static boolean isMedia(String extension) {
        return extension.toLowerCase() ==~ /[\s\S]*\.(png|jpg|jpeg|bmp|gif|tif|tiff|mp4|wmv|avi|flv|3gp|mkv)/
    }

    static boolean isVideo(String extension) {
        return extension.toLowerCase() ==~ /[\s\S]*\.(mp4|wmv|avi|flv|3gp|mkv)/
    }

    static boolean isImage(String extension) {
        return extension.toLowerCase() ==~ /[\s\S]*\.(png|jpg|jpeg|bmp|gif|tif|tiff)/
    }

    private static void tinyImage(String filename) {
        if (TinyImage.instance.isEnable()) {
            try {
                def args = ["python3", "tinyimages.py", "${Global.mediaPath}/${Global.imageTag}/${filename}"] as String[]
                processTinyImage(args)
            } catch (IOException e) {
                logger.info "Python3未安装，或默认Python3而链接到了python，现尝试使用python运行。消息：${e.message}"
                try {
                    def args = ["python", "tinyimages.py", "${Global.mediaPath}/${Global.imageTag}/${filename}"] as String[]
                    processTinyImage(args)
                } catch (IOException ex) {
                    logger.error "缩图失败，平台未安装Python。建议使用Python3，请安装。消息：${ex.message}"
                }
            }
        } else {
            logger.info("缩图功能尚未配置成功，不能执行缩图操作。")
        }
    }

    private static def processTinyImage = { String[] args ->
        def process = Runtime.runtime.exec args
        process.inputStream.eachLine { line ->
            logger.info line
        }
    }

}
