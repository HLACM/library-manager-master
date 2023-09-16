package com.zbw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbw.domain.User;
import com.zbw.utils.page.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends IService<User> {

    //用户登录
    User userLogin(String userName, String password);

    //更新用户信息
    boolean updateUser(User user, HttpServletRequest request);

    //查询用户借书记录

    //用户还书
    boolean userReturnBook(int bookId, HttpServletRequest request);

    //用户借书 
    boolean userBorrowingBook(int bookId, HttpServletRequest request);

    //分页查询用户
    Page<User> findUserByPage(int pageNum);

}
