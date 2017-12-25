package com.xmlan.machine.common.base

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
abstract class BaseService<T, DAO extends BaseDAO<T>> {

    public static final int DATABASE_DO_NOTHING = -1

    private Logger logger = LogManager.getLogger(getClass())
    public int pageSize = Global.getPageSize()

    @Autowired
    private DAO dao

    T get(String id) {
        logger.trace("${this.class.name}: get(${id}).")
        return dao.get(id)
    }

    List<T> findAll() {
        logger.trace("${this.class.name}: findAll().")
        return dao.findAll()
    }

    List<T> findList(T entity) {
        logger.trace("${this.class.name}: findList(${entity.toString()}).")
        return dao.findList(entity)
    }

    int insert(T entity) {
        logger.trace("${this.class.name}: insert(${entity.toString()}).")
        return dao.insert(entity)
    }

    int update(T entity) {
        logger.trace("${this.class.name}: update(${entity.toString()}).")
        return dao.update(entity)
    }

    int delete(T entity) {
        logger.trace("${this.class.name}: delete(${entity.toString()}).")
        return dao.delete(entity)
    }

}
