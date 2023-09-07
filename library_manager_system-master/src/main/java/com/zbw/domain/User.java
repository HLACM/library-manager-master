package com.zbw.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer userId;

    private String userName;

    private String userPwd;

    private String userEmail;

}