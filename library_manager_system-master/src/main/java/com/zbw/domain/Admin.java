package com.zbw.domain;

import lombok.Data;

import java.io.Serializable;


@Data
public class Admin implements Serializable {
    private Integer adminId;

    private String adminName;

    private String adminPwd;

    private String adminEmail;
}