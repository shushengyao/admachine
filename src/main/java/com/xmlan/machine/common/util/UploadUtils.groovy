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
        Map<String, List<String>> map = Maps.newHashMap()
        List<String> fileList = Lists.newArrayList()
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.session.servletContext)
        if (resolver.isMultipart(request)) { // 检查form中是否带有 enctype="multipart/form-data"
            // 转化normal-request为multipart-request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request
            Iterator iterator = multiRequest.fileNames // 获取multiRequest的全部文件名
            int i = 1 // 编号计数器
            while (iterator.hasNext()) { // 遍历所有文件
                MultipartFile file = multiRequest.getFile((++iterator).toString())
                try {
                    String extension = file.originalFilename.substring(file.originalFilename.lastIndexOf('.'))
                    if (file != null && isMedia(file.originalFilename)) {
                        // 日期_文件编号.后缀名
                        String filename = "${DateUtils.GetDate('yyyy.MM.dd.HH.mm.ss_No')}${i}${extension}"
                        try { // 存储文件
                            if (isImage(extension)) {
                                file.transferTo(new File("${Global.imagePath}/${filename}"))
                            }
                            if (isVideo(extension)) {
                                file.transferTo(new File("${Global.videoPath}/${filename}"))
                            }
                        } catch (IOException e) {
                            logger.debug e
                        }
                        fileList.add "${Global.apacheServer}/${filename}"
                        i++
                        logger.trace "Added media: ${filename}"
                    }
                } catch (StringIndexOutOfBoundsException ignored) {
                    logger.error "没有选中文件！"
                }
            }
        }
        map.put MEDIA_KEY, fileList
        logger.trace "map size: ${map.size()}\tfileList size: ${fileList.size()}"
        return fileList.isEmpty() ? "[]" : JsonUtils.toJsonString(map)
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
            String[] args = ["python3", "tinyimages.py", "${Global.imagePath}/${filename}"] as String[]
            Process process = Runtime.getRuntime().exec(args)
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))
            String line
            while ((line = input.readLine()) != null) {
                System.out.println(line)
            }
            input.close()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

}
