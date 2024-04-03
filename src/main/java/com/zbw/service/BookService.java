package com.zbw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbw.domain.Book;
import com.zbw.domain.Vo.BookVo;
import com.zbw.utils.page.Page;

import java.util.List;

public interface BookService extends IService<Book> {

    /**
     * 根据 书籍的部分信息 去数据库中查找书籍
     *
     * @param partInfo
     * @return
     */
    List<BookVo> selectBooksByBookPartInfo(String partInfo);

    /**
     * 根据书籍种类id查找书籍,分页查找
     *
     * @param categoryId
     * @return
     */
    Page<BookVo> findBooksByCategoryId(int categoryId, int pageNum);
}
