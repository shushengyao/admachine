package com.xmlan.machine.common.base
/**
 * Created by ayakurayuki on 2017/12/11-14:24.
 * Package: com.xmlan.machine.common.base
 */
interface BaseDAO<T> {

    T get(String id)

    List<T> findAll()

    List<T> findList(T t)

    int insert(T t)

    int update(T t)

    int delete(T t)

}
