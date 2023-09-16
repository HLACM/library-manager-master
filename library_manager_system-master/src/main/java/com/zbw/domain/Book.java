package com.zbw.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@TableName("book")
@Data
public class Book implements Serializable {
    @TableId(value = "book_id", type = IdType.AUTO)
    private Integer bookId;

    private String bookName;

    private String bookAuthor;

    private String bookPublish;

    private Integer bookCategory;

    private Double bookPrice;

    private String bookIntroduction;

}