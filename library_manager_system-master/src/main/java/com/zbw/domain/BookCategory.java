package com.zbw.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookCategory implements Serializable {
    private Integer categoryId;

    private String categoryName;
}