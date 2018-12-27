package com.xmlan.machine.module.led_machine.dao;

import com.xmlan.machine.common.base.BaseDAO;
import com.xmlan.machine.module.led_machine.entity.Led_machine
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: admachine
 * @description: dao
 * @author: YSS
 * @create: 2018-07-25 18:10
 **/
@Repository
interface Led_machineDAO  extends BaseDAO<Led_machine> {
    /**
     * 根据设备id查询led广告列表
     * @param machine_id
     * @return
     */
    List<Led_machine> findAllByUserID (@RequestParam("user_id") int user_id)
    /**
     * 根据id删除
     * @param id
     * @return
     */
    int delete(@RequestParam("id") int id)

    /**
     * 根据id查询
     * @param id
     * @return
     */
    List<Led_machine> select(@RequestParam("id") int id)
    /**
     * 查询所有
     * @return
     */
    List<Led_machine> findAll()

    /**
     * 变更节目播放列表
     * @param led
     * @return
     */
    boolean updatePlayList(@Param("play_list") String play_list, @Param("led") String led)

    /**
     * 查询播放列表
     * @param led
     * @return
     */
    String selectPlayList(@Param("led")String led)
}
