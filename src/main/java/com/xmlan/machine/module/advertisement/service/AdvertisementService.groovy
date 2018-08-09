package com.xmlan.machine.module.advertisement.service

import com.github.pagehelper.PageHelper
import com.google.common.collect.Lists
import com.xmlan.machine.common.base.BaseService
import com.xmlan.machine.common.cache.AdvertisementCache
import com.xmlan.machine.common.config.Global
import com.xmlan.machine.common.util.JsonUtils
import com.xmlan.machine.common.util.StringUtils
import com.xmlan.machine.common.util.UploadUtils
import com.xmlan.machine.module.advertisement.dao.AdvertisementDAO
import com.xmlan.machine.module.advertisement.entity.Advertisement
import com.xmlan.machine.module.advertisement.entity.AdvertisementCount
import com.xmlan.machine.module.advertisementMachine.entity.AdvertisementMachine
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestParam
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

    private static int deleteMedia(Advertisement entity) {
        if (UploadUtils.isMedia(entity.url)) {
            new File("${Global.mediaPath}/${entity.url}").delete()
            return DONE
        } else {
            return LOST_MEDIA_RESOURCE_WHEN_DELETE
        }
    }

    @Override
    int delete(Advertisement entity) {
        def result = deleteMedia(entity) // 删除条目前删除媒体文件
        super.delete(entity)
        return result
    }

    /**
     * 根据用户创建人查询广告列表
     * @param userID
     * @return
     */
    List<Advertisement> findListByUserID(@RequestParam("userID") int userID,@RequestParam("pageNo") int pageNo){
        PageHelper.startPage pageNo, pageSize
        dao.findListByUserID(userID)
    }
    /**
     * 根据用户创建人查询广告列表
     * @param userID
     * @return
     */
    List<Advertisement> findListByUserID(@RequestParam("userID") int userID){
        dao.findListByUserID(userID)
    }
    /**
     * 页面上传广告媒体资源
     * @param id 广告ID
     * @param request
     * @return 操作响应码
     */
    int uploadMedia(String id, HttpServletRequest request) {
        def advertisement = dao.get(id)
        deleteMedia(advertisement) // 更新前删除旧的媒体文件
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
        if (StringUtils.isNotBlank(advertisement.url)) {
            deleteMedia(advertisement) // 更新前删除旧的媒体文件
        }
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

    /**
     * 根据用户id查询设备列表
     * @param user_id
     * @return
     */
    List<Advertisement> findMachineByUserID(@RequestParam("user_id") int user_id,@RequestParam("pageNo") int pageNo){
        PageHelper.startPage pageNo, pageSize
        dao.findMachineByUserID(user_id)
    }

    /**
     * 根据设备id查询广告列表
     * @param machine_id
     * @return
     */
    List<Advertisement> findListByMachineID(int machine_id){
        dao.findListByMachineID(machine_id)
    }

}
