package com.zbw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbw.domain.*;
import com.zbw.mapper.BookMapper;
import com.zbw.mapper.BorrowingBooksMapper;
import com.zbw.mapper.UserMapper;
import com.zbw.service.BorrowingBooksService;
import com.zbw.service.UserService;
import com.zbw.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Resource
    private BorrowingBooksService borrowingBooksService;


    @Override
    public User userLogin(String userName, String password) {
        List<User> users = this.query().eq("user_name", userName).list();
        if (null == users) {
            return null;
        }
        for (User user : users) {
            if (user.getUserPwd().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updateUser(User user, HttpServletRequest request) {
        //获取session对象中user对象
        User sessionUser = (User) request.getSession().getAttribute("user");
        user.setUserId(sessionUser.getUserId());

        boolean n = this.updateById(user);
        if (n) {
            //修改成功，更新session对象
            User newUser = this.getById(user.getUserId());
            request.getSession().setAttribute("user", newUser);
            return true;
        }
        return false;
    }





    @Override
    public boolean userBorrowingBook(int bookId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        //检查该书是否可借,从借书记录表中查询该书是否已借出
        List<BorrowingBooks> list = borrowingBooksService.query().eq("book_id", bookId).list();
        if (list.size() > 0) {
            return false;
        }

        BorrowingBooks borrowingBooks = new BorrowingBooks();
        borrowingBooks.setBookId(bookId);
        borrowingBooks.setUserId(user.getUserId());
        borrowingBooks.setDate(new Date());
        boolean n=false;
        try {
            //数据库中增加一条借书记录 【如果插入失败 , 则借书失败】
            n = borrowingBooksService.save(borrowingBooks);
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }

       return n;
    }


    /**
     * 根据传来的页码参数查询要显示的数据，返回对应封装好数据的page对象
     * @param pageNum
     * @return
     */
    @Override
    public Page<User> findUserByPage(int pageNum) {
        List<User> users = getBaseMapper().selectByPageNum((pageNum - 1) * 10, 10);
        Page<User> page = new Page<>();
        page.setList(users);
        page.setPageNum(pageNum);
        page.setPageSize(10);
        int userCount = this.count();
        int pageCount = userCount / 10;
        if (userCount % 10 != 0) {
            pageCount++;
        }
        page.setPageCount(pageCount);
        return page;
    }

}
