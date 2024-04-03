package com.zbw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommentController {

    /**
     * 点击录入新书返回添加书籍页面
     */
    @RequestMapping("/addBookPage")
    public String addBookPage() {
        return "admin/addBook";
    }

    /**
     * 返回新增用户页面
     */
    @RequestMapping("/addUserPage")
    public String addUserPage() {
        return "admin/addUser";
    }

    @RequestMapping("/adminInfoPage")
    public String adminInfo() {
        return "admin/adminInfo";
    }

    /**
     * 返回归还书籍页面
     */
    @RequestMapping("/userReturnBooksPage")
    public String userReturnBooksPage() {
        return "user/returnBooks";
    }

    /**
     * 返回借书页面
     */
    @RequestMapping("/borrowingPage")
    public String borrowing() {
        return "user/borrowingBooks";
    }

    /**
     * 返回用户查询书籍页面
     */
    @RequestMapping("/findBookPage")
    public String findBookPage() {
        return "user/findBook";
    }



}
