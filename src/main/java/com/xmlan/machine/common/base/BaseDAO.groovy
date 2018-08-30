package com.xmlan.machine.common.base

/**
 * Created by ayakurayuki on 2017/12/11-14:24. <br/>
 * Package: com.xmlan.machine.common.base <br/>
 * DAO基类 <br/>
 */
interface BaseDAO<T> {

    T get(String id)

    List<T> findAll()

    List<T> findList(T t)

//    List<T> findAll(T t)

    int insert(T t)

    int update(T t)

    int delete(T t)

}
