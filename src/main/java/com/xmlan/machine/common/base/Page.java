package com.xmlan.machine.common.base;

import com.xmlan.machine.common.config.Global;

import java.util.List;

/**
 * Created by Ayakura Yuki on 2017/7/24.
 */
public class Page<T> {

    private int pageNo = 1; // 当前页, 默认为第1页
    private int pageSize = Global.getPageSize(); // 每页记录数
    private long totalRecord = -1; // 总记录数, 默认为-1, 表示需要查询
    private int totalPage = -1; // 总页数, 默认为-1, 表示需要计算

    protected List<T> list; // 当前页记录List形式

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        computeTotalPage();
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
        computeTotalPage();
    }

    public void computeTotalPage() {
        if (getPageSize() > 0 && getTotalRecord() > -1) {
            this.totalPage =
                    (int) (getTotalRecord() % getPageSize() == 0 ?
                            getTotalRecord() / getPageSize()
                            : getTotalRecord() / getPageSize() + 1);
        }
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Page [pageNo=" + pageNo + ", pageSize=" + pageSize +
                ", totalRecord=" + (totalRecord < 0 ? "null" : totalRecord) + ", totalPage=" +
                (totalPage < 0 ? "null" : totalPage) + ", list=" + (list == null ? "null" : list) + "]";
    }

}
