package com.zbw.controller;

import com.zbw.domain.Admin;
import com.zbw.domain.BookCategory;
import com.zbw.domain.User;
import com.zbw.domain.Vo.BookVo;
import com.zbw.service.IAdminService;
import com.zbw.service.IBookCategoryService;
import com.zbw.service.IUserService;
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
import java.util.concurrent.TimeUnit;

@Controller
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IBookCategoryService bookCategoryService;
    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 管理员登陆功能，匹配失败继续待在登录页面，成功登录主页
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/adminLogin")
    public String adminLogin(@Param("userName") String userName, @Param("password") String password, HttpServletRequest request) {
       //得到一个对应的Admin对象
        Admin admin = adminService.adminLogin(userName, password);

        if (admin == null) {//对象为空，未匹配到用户
            //向前端发送数据，flag 为 1 表示 登录失败
            request.getSession().setAttribute("flag", 1);
            //返回到登录页面index中
            return "index";
        }
        // flag = 0 表示用户名密码校验成功
        request.getSession().setAttribute("flag", 0);
        request.getSession().setAttribute("admin", admin);
        //返回路径登录到主页
        return "admin/index";
    }

    /**
     * 点击录入新书返回添加书籍页面
     */
    @RequestMapping("/addBookPage")
    public String addBookPage() {
        return "admin/addBook";
    }

    /**
     * 点击新建类别返回类别页面，默认展示第一页
     * 使用Redis缓存技术，先查询redis中是否有数据，有则返回redis中数据，没有则存入数据入Redis中
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/addCategoryPage")
    public String addCategoryPage(@RequestParam("pageNum") int pageNum, Model model) {
        Page<BookCategory> page=null;
        //定义动态键
        String key="addCategoryPage"+"_"+pageNum;
        //先从Redis中查询数据，如果有数据则直接返回
        page=(Page<BookCategory>)redisTemplate.opsForValue().get(key);
        if(page!=null){
            model.addAttribute("page", page);
            return "admin/addCategory";
        }
        page = bookCategoryService.selectBookCategoryByPageNum(pageNum);
        //将查询出来的数据存入redis中，key名就是方法的名字
        redisTemplate.opsForValue().set(key,page,30, TimeUnit.MINUTES);
        //model.addAttribute用于封装前端页面返回需要的数据，在前端页面中，使用EL表达式"${}"获取Map中的数据
        model.addAttribute("page", page);
        return "admin/addCategory";
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
     * 返回&emsp;&emsp;查询书籍页面。和上面不同，未查询书籍前先不显示数据，把页码和页面总数都设置为1
     * @param model
     * @return
     */
    @RequestMapping("/showBooksPage")
    public String showBooksPage(Model model) {
        Page<BookVo> page = new Page<BookVo>();
        page.setPageCount(1);
        page.setPageNum(1);
        model.addAttribute("page", page);
        return "admin/showBooks";
    }


    /**
     * 管理员退出登陆
     *
     * @param request
     * @return
     */
    @RequestMapping("/adminLogOut")
    public String userLogOut(HttpServletRequest request) {
        //把对应存入request的信息删除掉再返回到登录页面
        request.getSession().invalidate();
        return "index";
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
     * 更新管理员信息
     *
     * @param admin
     * @param request
     * @return
     */
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public boolean updateAdmin(Admin admin, HttpServletRequest request) {
        return adminService.updateAdmin(admin, request);
        //该方法返回一个boolean值，前端根据接收到的boolean值进行页面提示
    }

}
