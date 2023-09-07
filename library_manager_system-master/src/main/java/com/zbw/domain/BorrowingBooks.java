package com.zbw.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BorrowingBooks implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer bookId;

    private Date date;
}