package com.zbw.utils.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页功能的实现
 * @param <T>
 */

@Data
public class Page<T> implements Serializable {
    private List<T> list;//T类型的对象链表
    private int pageNum; //当前页码
    private int pageSize;//每页数量
    private int pageCount;//总页数
}
