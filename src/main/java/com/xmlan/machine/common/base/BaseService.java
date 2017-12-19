package com.xmlan.machine.common.base;

import com.xmlan.machine.common.config.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ayakurayuki on 2017/12/11-14:26.
 * Package: com.xmlan.machine.common.base
 */
@Transactional(readOnly = true)
public abstract class BaseService<T, DAO extends BaseDAO<T>> {

    public static final int DATABASE_DO_NOTHING = -1;

    protected Logger logger = LogManager.getLogger(getClass());
    protected int pageSize = Global.getPageSize();

    @Autowired
    private DAO dao;

    public T get(String id) {
        return dao.get(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    public int insert(T entity) {
        return dao.insert(entity);
    }

    public int update(T entity) {
        return dao.update(entity);
    }

    public int delete(T entity) {
        return dao.delete(entity);
    }

}
