package com.zbw.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {
    private Integer bookId;

    private String bookName;

    private String bookAuthor;

    private String bookPublish;

    private Integer bookCategory;

    private Double bookPrice;

    private String bookIntroduction;

}