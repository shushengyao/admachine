package com.xmlan.machine.common.base

import com.xmlan.machine.common.config.Global

/**
 * Created by Ayakura Yuki on 2017/7/24.
 */
class Page<T> {

    private int pageNo = 1
    // 当前页, 默认为第1页
    private int pageSize = Global.pageSize
    // 每页记录数
    private long totalRecord = -1
    // 总记录数, 默认为-1, 表示需要查询
    private int totalPage = -1
    // 总页数, 默认为-1, 表示需要计算

    protected List<T> list
    // 当前页记录List形式

    int getPageNo() {
        return pageNo
    }

    void setPageNo(int pageNo) {
        this.pageNo = pageNo
    }

    int getPageSize() {
        return pageSize
    }

    void setPageSize(int pageSize) {
        this.pageSize = pageSize
        computeTotalPage()
    }

    long getTotalRecord() {
        return totalRecord
    }

    int getTotalPage() {
        return totalPage
    }

    void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord
        computeTotalPage()
    }

    void computeTotalPage() {
        if (pageSize > 0 && totalRecord > -1) {
            this.totalPage = (int) (
                    totalRecord % pageSize == 0 ?
                            totalRecord / pageSize :
                            totalRecord / pageSize + 1
            )
        }
    }

    List<T> getList() {
        return list
    }

    void setList(List<T> list) {
        this.list = list
    }

}
