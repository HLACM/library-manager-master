package com.zbw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbw.domain.*;
import com.zbw.mapper.BookMapper;
import com.zbw.mapper.BorrowingBooksMapper;
import com.zbw.mapper.UserMapper;
import com.zbw.service.UserService;
import com.zbw.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BorrowingBooksMapper borrowingBooksMapper;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<User> findUserByUserName(String userName) {
        
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        
        criteria.andUserNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(userExample);
        
        return users;
    }



    @Override
    public User userLogin(String userName, String password) {
        
        List<User> users = findUserByUserName(userName);
        
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
        
        int n = userMapper.updateByPrimaryKey(user);

        if (n > 0) {
            //修改成功，更新session对象
            User newUser = userMapper.selectByPrimaryKey(user.getUserId());
            request.getSession().setAttribute("user", newUser);
            return true;
        }

        return false;
    }



    @Override
    public boolean userReturnBook(int bookId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        BorrowingBooksExample borrowingBooksExample = new BorrowingBooksExample();
        BorrowingBooksExample.Criteria criteria = borrowingBooksExample.createCriteria();
        criteria.andUserIdEqualTo(user.getUserId());
        criteria.andBookIdEqualTo(bookId);

        //删除数据库中user_d等于userId,book_id等于bookId的记录
        int n = borrowingBooksMapper.deleteByExample(borrowingBooksExample);
        if (n > 0) return true;
        return false;
    }

    @Override
    public boolean userBorrowingBook(int bookId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        //检查该书是否可借,从借书记录表中查询该书是否已借出
        BorrowingBooksExample borrowingBooksExample = new BorrowingBooksExample();
        BorrowingBooksExample.Criteria criteria = borrowingBooksExample.createCriteria();
        criteria.andBookIdEqualTo(bookId);
        List<BorrowingBooks> list = borrowingBooksMapper.selectByExample(borrowingBooksExample);

        if (list.size() > 0) {
            return false;
        }

        BorrowingBooks borrowingBooks = new BorrowingBooks();
        borrowingBooks.setBookId(bookId);
        borrowingBooks.setUserId(user.getUserId());
        borrowingBooks.setDate(new Date());
        int n = 0;

        try {
            //数据库中增加一条借书记录 【如果插入失败 , 则借书失败】
            n = borrowingBooksMapper.insert(borrowingBooks);
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }


        if (n > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User findUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据传来的页码参数查询要显示的数据，返回对应封装好数据的page对象
     * @param pageNum
     * @return
     */
    @Override
    public Page<User> findUserByPage(int pageNum) {
        List<User> users = userMapper.selectByPageNum((pageNum - 1) * 10, 10);
        Page<User> page = new Page<>();
        page.setList(users);
        page.setPageNum(pageNum);
        page.setPageSize(10);

        int userCount = userMapper.selectUserCount();
        int pageCount = userCount / 10;
        if (userCount % 10 != 0) {
            pageCount++;
        }
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteUserById(int userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }
}
