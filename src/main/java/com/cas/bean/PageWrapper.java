package com.cas.bean;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/6/7 4:14 下午
 * @desc 分页包装
 */
public class PageWrapper {

    /**
     * 总数
     */
    private int total;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 第几页
     */
    private int pageNum;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
