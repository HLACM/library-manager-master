package com.zbw.controller;


import com.zbw.domain.User;
import com.zbw.domain.Vo.BorrowingBooksVo;
import com.zbw.service.BorrowingBooksService;
import com.zbw.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowingBooksService borrowingBooksRecordService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 用户登录（与管理员登录一样）
     *
     * @param userName
     * @return
     */
    @PostMapping("/userLogin")
    public String userLogin(@Param("userName") String userName,
                            @Param("password") String password, HttpServletRequest request) {
        User user = userService.userLogin(userName, password);
        
        if (null != user) {
            // flag = 0 表示用户名密码校验成功  【用于前端校验】
            request.getSession().setAttribute("flag", 0);

            request.getSession().setAttribute("user", user);
            return "user/index";
        }

        // flag 为 1 表示 登录失败 【用于前端校验】
        request.getSession().setAttribute("flag", 1);
        return "index";
    }



    /**
     * 返回用户借书记录页面
     * 使用Redis缓存技术
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/userBorrowBookRecord")
    public String userBorrowBookRecord(Model model, HttpServletRequest request) {
        ArrayList<BorrowingBooksVo> res=null;
        //获取到user对象，并将userId提取出来对Key进行动态命名
        User user = (User) request.getSession().getAttribute("user");
        String key="userBorrowBookRecord"+"_"+user.getUserId();
        res=(ArrayList<BorrowingBooksVo>)redisTemplate.opsForValue().get(key);
        if (res!=null){
            model.addAttribute("borrowingBooksList", res);
            return "user/borrowingBooksRecord";
        }
        //不用分页，返回一个借书记录的列表，数据存进去，未查询到缓存则封装好查询出来的数据并存入Redis
        res = borrowingBooksRecordService.selectAllBorrowRecord(request);
        redisTemplate.opsForValue().set(key,res,30, TimeUnit.MINUTES);

        model.addAttribute("borrowingBooksList", res);
        return "user/borrowingBooksRecord";
    }

    /**
     * 返回归还书籍页面
     */
    @RequestMapping("/userReturnBooksPage")
    public String userReturnBooksPage() {
        return "user/returnBooks";
    }

    /**
     * 搜索并保存用户个人信息并返回页面
     */
    @RequestMapping("/userMessagePage")
    public String userMessagePage(Model model, HttpServletRequest request) {
        User session_user = (User) request.getSession().getAttribute("user");
        User user = userService.findUserById(session_user.getUserId());
        model.addAttribute("message_user", user);
        return "user/userMessage";
    }

    /**
     * 返回借书页面
     */
    @RequestMapping("/borrowingPage")
    public String borrowing() {
        return "user/borrowingBooks";
    }


    /**
     * 更新用户信息
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public boolean updateUser(User user, HttpServletRequest request) {
        return userService.updateUser(user, request);
    }

    /**
     * 用户还书
     * 归还书籍成功之后删除掉对应Redis缓存中的数据
     * @param bookId
     * @param request
     * @return
     */
    @RequestMapping("/userReturnBook")
    @ResponseBody
    public boolean returnBook(int bookId, HttpServletRequest request) {
        User user=(User) request.getSession().getAttribute("user");
        boolean a=userService.userReturnBook(bookId, request);
        if(a){
            redisTemplate.delete("userBorrowBookRecord"+"_"+user.getUserId());
        }
        return a;
    }

    /**
     * 用户借书
     * 借书后删除旧Redis缓存
     * @param bookId
     * @param request
     * @return
     */
    @RequestMapping("/userBorrowingBook")
    @ResponseBody
    public boolean borrowingBook(int bookId, HttpServletRequest request) {
        User user=(User) request.getSession().getAttribute("user");
        boolean a=userService.userBorrowingBook(bookId, request);
        if(a){
            redisTemplate.delete("userBorrowBookRecord"+"_"+user.getUserId());
        }
        return a;
    }



    /**
     * 用户退出登陆
     *
     * @param request
     * @return
     */
    @RequestMapping("/userLogOut")
    public String userLogOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    /**
     * 返回用户查询书籍页面
     */
    @RequestMapping("/findBookPage")
    public String findBookPage() {
        return "user/findBook";
    }


    /**
     * 根据用户id删除用户
     *
     * @param userId
     * @return
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUserByUserId(@RequestParam("userId") int userId) {
        int res = userService.deleteUserById(userId);
        if (res > 0) {
            return "true";
        }
        return "false";
    }

    /**
     * 添加用户
     * 添加后删除掉旧缓存
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(User user) {
        int res = userService.insertUser(user);
        if (res > 0) {
            //清理掉所有用户的缓存数据
            Set keys=redisTemplate.keys("showUsersPage_*");
            redisTemplate.delete(keys);
            return "true";
        } else {
            return "false";
        }
    }
}
