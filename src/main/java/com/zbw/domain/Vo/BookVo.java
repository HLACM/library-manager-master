package com.zbw.domain.Vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookVo implements Serializable {
    private Integer bookId;  //书籍id

    private String bookName; //书名

    private String bookAuthor;//作者

    private String bookPublish;//出版社

    private String isExist;  //是否可借

}
