package com.zbw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbw.domain.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService extends IService<Admin> {


    //管理员登陆
    Admin adminLogin(String name, String password);


    // 更新管理员信息
    boolean updateAdmin(Admin admin, HttpServletRequest request);
}
