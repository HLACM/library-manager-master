package com.zbw.domain.Vo;

import com.zbw.domain.Book;
import com.zbw.domain.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zbw
 * 添加视图层对象
 * 新增属性 user
 */
@Data
public class BorrowingBooksVo implements Serializable {
    private User user;
    private Book book;  //借阅书籍
    private String dateOfBorrowing;  //借书日期
    private String dateOfReturn;    //还书日期
}
