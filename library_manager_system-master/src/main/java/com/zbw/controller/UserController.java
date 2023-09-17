package com.zbw.controller;


import com.zbw.domain.User;
import com.zbw.service.UserService;
import com.zbw.utils.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Controller
public class UserController {

    @Resource
    private UserService userService;


    @Resource
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
     * 搜索并保存用户个人信息并返回页面
     */
    @RequestMapping("/userMessagePage")
    public String userMessagePage(Model model, HttpServletRequest request) {
        User session_user = (User) request.getSession().getAttribute("user");
        User user = userService.getById(session_user.getUserId());
        model.addAttribute("message_user", user);
        return "user/userMessage";
    }


    /**
     * 返回查询用户页面，和上面的代码差不多
     * 使用Redis缓存技术
     * @param model
     * @param pageNum
     * @return
     */
    @RequestMapping("/showUsersPage")
    public String showUsersPage(Model model, @RequestParam("pageNum") int pageNum) {
        Page<User> page=null;
        String key="showUsersPage"+"_"+pageNum;
        page=(Page<User>)redisTemplate.opsForValue().get(key);
        if (page!=null){
            model.addAttribute("page", page);
            return "admin/showUsers";
        }
        page = userService.findUserByPage(pageNum);
        redisTemplate.opsForValue().set(key,page,30, TimeUnit.MINUTES);
        model.addAttribute("page", page);
        return "admin/showUsers";
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
     * 根据用户id删除用户
     *
     * @param userId
     * @return
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUserByUserId(@RequestParam("userId") int userId) {
        boolean res = userService.removeById(userId);
        if (res) {
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
        boolean res = userService.save(user);
        if (res) {
            //清理掉所有用户的缓存数据
            Set keys=redisTemplate.keys("showUsersPage_*");
            redisTemplate.delete(keys);
            return "true";
        } else {
            return "false";
        }
    }
}
