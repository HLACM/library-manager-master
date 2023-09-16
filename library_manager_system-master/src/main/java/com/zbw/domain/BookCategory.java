package com.zbw.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@TableName("book_category")
@Data
public class BookCategory implements Serializable {
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    private String categoryName;
}