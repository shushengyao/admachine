package com.xmlan.machine.common.base

import com.github.pagehelper.PageHelper
import com.xmlan.machine.common.config.Global
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2017/12/11-14:26. <br/>
 * Package: com.xmlan.machine.common.base <br/>
 * Service基类 <br/>
 */
@Transactional(readOnly = true)
abstract class BaseService<T, DAO extends BaseDAO<T>> extends BaseBean {

    protected Logger logger = LogManager.getLogger(getClass())
    public int pageSize = Global.pageSize

    @Autowired
    protected DAO dao

    /**
     * 使用ID获取对象
     * @param id 对象ID
     * @return 查找到的对象 ( 或null )
     */
    T get(String id) {
        logger.trace "${this.class.name}: get(${id}).".toString()
        dao.get id
    }

    /**
     * 查询全部条目
     * @return 全部条目列表 ( 或null )
     */
    List<T> findAll() {
        logger.trace "${this.class.name}: findAll().".toString()
        dao.findAll()
    }

    /**
     * 条件查询
     * @param entity 控制条件所用的对象
     * @param pageNo 分页页数
     * @return 查询到的条目列表 ( 或null )
     */
    List<T> findList(T entity, int pageNo) {
        logger.trace "${this.class.name}: findList(${entity.toString()}).".toString()
        PageHelper.startPage pageNo, pageSize
        dao.findList entity
    }

    /**
     * 插入对象
     * @param entity
     * @return
     */
    int insert(T entity) {
        logger.trace "${this.class.name}: insert(${entity.toString()}).".toString()
        dao.insert entity
    }

    /**
     * 修改对象
     * @param entity
     * @return
     */
    int update(T entity) {
        logger.trace "${this.class.name}: update(${entity.toString()}).".toString()
        dao.update entity
    }

    /**
     * 删除条目
     * @param entity
     * @return
     */
    int delete(T entity) {
        logger.trace "${this.class.name}: delete(${entity.toString()}).".toString()
        dao.delete entity
    }

}
