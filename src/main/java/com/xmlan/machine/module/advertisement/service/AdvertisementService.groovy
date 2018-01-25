package com.xmlan.machine.module.advertisement.service

import com.google.common.collect.Lists
import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.config.Global
import com.xmlan.machine.common.util.JsonUtils
import com.xmlan.machine.common.util.UploadUtils
import com.xmlan.machine.module.advertisement.dao.AdvertisementDAO
import com.xmlan.machine.module.advertisement.entity.Advertisement
import com.xmlan.machine.module.advertisement.entity.AdvertisementCount
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletRequest

/**
 * Created by ayakurayuki on 2017/12/13-08:57.
 * <p>
 * Package: com.xmlan.machine.module.advertisement.service
 * <p>
 * 广告业务逻辑层
 */
@Service("AdvertisementService")
@Transactional(readOnly = true)
class AdvertisementService extends BaseService<Advertisement, AdvertisementDAO> {

    @Override
    int delete(Advertisement entity) {
        def fileURL = entity.url
        super.delete(entity)
        if (UploadUtils.isMedia(fileURL)) {
            new File("${Global.mediaPath}/${fileURL}").delete()
            return DONE
        } else {
            return LOST_MEDIA_RESOURCE_WHEN_DELETE
        }
    }

    /**
     * 页面上传广告媒体资源
     * @param id 广告ID
     * @param request
     * @return 操作响应码
     */
    int uploadMedia(String id, HttpServletRequest request) {
        def advertisement = dao.get(id)
        def jsonString = UploadUtils.uploadImages(request)
        if (jsonString == '[]') {
            return FAILURE
        }
        def json = JsonUtils.fromJsonString(jsonString, Map.class) as Map
        def list = json.get(UploadUtils.MEDIA_KEY) as List<String>
        advertisement.url = list.get(0)
        update(advertisement)
        return DONE
    }

    /**
     * 移动终端上传广告媒体资源
     * @param id 广告ID
     * @param time 播放时间
     * @param file 文件
     * @return 操作响应码
     */
    int uploadMedia(String id, int time, MultipartFile file) {
        def advertisement = dao.get(id)
        def jsonString = UploadUtils.uploadImages(file)
        if (jsonString == '[]') {
            return FAILURE
        }
        def json = JsonUtils.fromJsonString(jsonString, Map.class) as Map
        def list = json.get(UploadUtils.MEDIA_KEY) as List<String>
        advertisement.url = list.get(0)
        advertisement.time = time
        update(advertisement)
        return DONE
    }

    /**
     * 获取广告数统计
     * @param list 包含广告机的列表
     * @return 包含统计数的列表
     */
    static List<AdvertisementCount> getAdvertisementCount(List<AdvertisementMachine> list) {
        List<AdvertisementCount> counts = Lists.newArrayList()
        list.each {
            def count = new AdvertisementCount()
            count.id = it.id
            count.count = AdvertisementCache.getAdvertisementCountByMachineID(it.id)
            counts.add(count)
        }
        return counts
    }

    /**
     * 移动终端无分页查询列表
     * @param advertisement
     * @return
     */
    List<Advertisement> findList(Advertisement advertisement) {
        dao.findList advertisement
    }

}
