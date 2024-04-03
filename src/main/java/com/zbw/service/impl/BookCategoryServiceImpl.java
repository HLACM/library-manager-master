package com.zbw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbw.domain.BookCategory;
import com.zbw.mapper.BookCategoryMapper;
import com.zbw.service.BookCategoryService;
import com.zbw.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements BookCategoryService {

    /**
     * 根据传来的页码参数查询要显示的数据，返回对应封装好数据的page对象
     * @param pageNum
     * @return
     */
    @Override
    public Page<BookCategory> selectBookCategoryByPageNum(int pageNum) {
        Page<BookCategory> page = new Page<>();
        //给出当前页索引，查询对应页，展示十条数据
        List<BookCategory> list = getBaseMapper().selectByPageNum((pageNum - 1) * 10, 10);
        //补充page对象对应参数
        page.setPageSize(10);
        page.setPageNum(pageNum);
        page.setList(list);
        int recordCount = this.count();
        int pageCount = recordCount / 10;
        if (recordCount % 10 != 0) {
            pageCount++;
        }
        page.setPageCount(pageCount);
        return page;
    }

}
