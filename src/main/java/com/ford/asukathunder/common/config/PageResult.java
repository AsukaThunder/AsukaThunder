package com.ford.asukathunder.common.config;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 * @ClassName: PageResult
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/10 上午 9:50
 **/
@Setter
@Getter
public class PageResult<T> implements Serializable {
    /**
     * 页数
     */
    private long page;
    /**
     * 总数
     */
    private long total;
    /**
     * 数据
     */
    private List<T> list;

    public PageResult(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public PageResult(long page, long total, List<T> list) {
        this.total = total;
        this.page = page;
        this.list = list;
    }
}
