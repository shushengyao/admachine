package com.xmlan.machine.common.base

import com.github.pagehelper.PageHelper
import com.xmlan.machine.common.config.Global
import net.sf.ehcache.CacheManager
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
    public static final int ADMIN_ROLE_ID = 1
    public static final int DONE = 0
    public static final int ADMIN_DONE = 10
    public static final int INCORRECT_REPASSWD = 11
    public static final int INCORRECT_OLDPASSWD = 12

    protected Logger logger = LogManager.getLogger(getClass())
    public int pageSize = Global.getPageSize()

    @Autowired
    protected DAO dao

    @Autowired
    protected CacheManager cacheManager

    T get(String id) {
        logger.trace "${this.class.name}: get(${id})."
        return dao.get(id)
    }

    List<T> findAll() {
        logger.trace "${this.class.name}: findAll()."
        return dao.findAll()
    }

    List<T> findList(T entity, int pageNo) {
        logger.trace "${this.class.name}: findList(${entity.toString()})."
        PageHelper.startPage pageNo, pageSize
        return dao.findList(entity)
    }

    int insert(T entity) {
        logger.trace "${this.class.name}: insert(${entity.toString()})."
        int result = dao.insert(entity)
        cacheManager.clearAll()
        return result
    }

    int update(T entity) {
        logger.trace "${this.class.name}: update(${entity.toString()})."
        int result = dao.update(entity)
        cacheManager.clearAll()
        return result
    }

    int delete(T entity) {
        logger.trace "${this.class.name}: delete(${entity.toString()})."
        int result = dao.delete(entity)
        cacheManager.clearAll()
        return result
    }

}
