package com.zbw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbw.domain.Admin;
import com.zbw.domain.Book;
import com.zbw.domain.BookCategory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAdminService extends IService<Admin> {


    //管理员登陆
    Admin adminLogin(String name, String password);


    // 更新管理员信息
    boolean updateAdmin(Admin admin, HttpServletRequest request);
}
