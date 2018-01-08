package com.xmlan.machine.common.base

import com.github.pagehelper.PageHelper
import com.xmlan.machine.common.config.Global
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ayakurayuki on 2017/12/11-14:26.
 * Package: com.xmlan.machine.common.base
 */
@Transactional(readOnly = true)
abstract class BaseService<T, DAO extends BaseDAO<T>> extends BaseBean {

    protected Logger logger = LogManager.getLogger(getClass())
    public int pageSize = Global.pageSize

    @Autowired
    protected DAO dao

    T get(String id) {
        logger.trace "${this.class.name}: get(${id})."
        dao.get id
    }

    List<T> findAll() {
        logger.trace "${this.class.name}: findAll()."
        dao.findAll()
    }

    List<T> findList(T entity, int pageNo) {
        logger.trace "${this.class.name}: findList(${entity.toString()})."
        PageHelper.startPage pageNo, pageSize
        dao.findList entity
    }

    int insert(T entity) {
        logger.trace "${this.class.name}: insert(${entity.toString()})."
        dao.insert entity
    }

    int update(T entity) {
        logger.trace "${this.class.name}: update(${entity.toString()})."
        dao.update entity
    }

    int delete(T entity) {
        logger.trace "${this.class.name}: delete(${entity.toString()})."
        dao.delete entity
    }

}
