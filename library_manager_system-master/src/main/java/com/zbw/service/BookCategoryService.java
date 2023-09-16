package com.zbw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbw.domain.BookCategory;
import com.zbw.utils.page.Page;

public interface BookCategoryService extends IService<BookCategory> {

    //图书类别分页查询
    public Page<BookCategory> selectBookCategoryByPageNum(int pageNum);

}
