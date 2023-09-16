package com.zbw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbw.domain.Admin;
import com.zbw.domain.BookCategory;
import com.zbw.mapper.AdminMapper;
import com.zbw.mapper.BookCategoryMapper;
import com.zbw.service.IBookCategoryService;
import com.zbw.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements IBookCategoryService {

    @Autowired
    private IBookCategoryService bookCategoryService;

    @Autowired
    private BookCategoryMapper bookCategoryMapper;

    /**
     * 根据传来的页码参数查询要显示的数据，返回对应封装好数据的page对象
     * @param pageNum
     * @return
     */
    @Override
    public Page<BookCategory> selectBookCategoryByPageNum(int pageNum) {
        Page<BookCategory> page = new Page<>();
        //给出当前页索引，查询对应页，展示十条数据
        List<BookCategory> list = bookCategoryMapper.selectByPageNum((pageNum - 1) * 10, 10);
        //补充page对象对应参数
        page.setPageSize(10);
        page.setPageNum(pageNum);
        page.setList(list);
        int recordCount = bookCategoryMapper.selectAllCount();
        int pageCount = recordCount / 10;
        if (recordCount % 10 != 0) {
            pageCount++;
        }
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public int deleteBookCategoryById(int bookCategoryId) {
        return bookCategoryMapper.deleteByPrimaryKey(bookCategoryId);
    }
}
