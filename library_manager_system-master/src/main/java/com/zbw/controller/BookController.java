package com.zbw.controller;

import com.zbw.domain.*;
import com.zbw.domain.Vo.BookVo;
import com.zbw.service.AdminService;
import com.zbw.service.BookCategoryService;
import com.zbw.service.BookService;
import com.zbw.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Controller
public class BookController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookCategoryService bookCategoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 管理员录入新书
     * 添加书籍后删除掉旧缓存，注意先后顺序，应对多线程并发问题
     * @param book
     * @return
     */
    @RequestMapping("/addBook")
    @ResponseBody
    public String addBook(Book book) {
        //方法返回一个布尔值
        boolean res = bookService.save(book);
        if (res) {
            //清理掉对应种类书籍的所有缓存数据
            Set keys=redisTemplate.keys("showBooksResultPageByCategoryId"+"_"+book.getBookCategory()+"_*");
            redisTemplate.delete(keys);
            return "true";
        }
        return "false";
    }

    /**
     * 返回管理员查询书籍结果页
     * 使用Redis对数据进行缓存
     * @param pageNum
     * @param bookCategory 为外部传入的CategoryId
     * @param model
     * @return
     */
    @RequestMapping("/showBooksResultPageByCategoryId")
    public String showBooksResultPageByCategoryId(@RequestParam("pageNum") int pageNum, @RequestParam("bookCategory") int bookCategory, Model model) {
        Page<BookVo> page=null;
        //用categoryId动态获取键名,pageNum代表第几页，bookCategory代表是哪一类的书籍
        String key="showBooksResultPageByCategoryId"+"_"+bookCategory+"_"+pageNum;
        page=(Page<BookVo>)redisTemplate.opsForValue().get(key);
        if (page!=null){
            model.addAttribute("page", page);
            model.addAttribute("bookCategory", bookCategory);
            return "admin/showBooks";
        }
        //未查询到缓存则封装好查询出来的数据并存入Redis
        page = bookService.findBooksByCategoryId(bookCategory, pageNum);
        redisTemplate.opsForValue().set(key,page,30,TimeUnit.MINUTES);
        model.addAttribute("page", page);
        model.addAttribute("bookCategory", bookCategory);
        return "admin/showBooks";
    }

    /**
     * 返回用户查询书籍结果页
     *
     * @param bookPartInfo
     * @return
     */
    @RequestMapping("/findBookByBookPartInfo")
    public String findBooksResultPage(@RequestParam("bookPartInfo") String bookPartInfo, Model model) {
        //参数为对应书籍的名字的一部分，通过部分名字查找对应的数据。由于展示的是一条数据所以不需要分页
        List<BookVo> bookVos = bookService.selectBooksByBookPartInfo(bookPartInfo);
        model.addAttribute("bookList", bookVos);
        return "user/findBook";
    }

    /**
     * 查询所有书籍种类（在录入新书的类别选择栏中显示所有类别）
     *
     * @return
     */
    @RequestMapping("/findAllBookCategory")
    @ResponseBody
    public List<BookCategory> findAllBookCategory() {
        return bookCategoryService.list();
    }

    /**
     * 新建书籍种类
     * 添加后删除掉旧缓存
     * @param bookCategory
     * @return
     */
    @RequestMapping("/addBookCategory")
    @ResponseBody
    public String addBookCategory(BookCategory bookCategory) {
        boolean b = bookCategoryService.save(bookCategory);
        if (b) {
            //清理掉所有该种类的缓存数据
            Set keys=redisTemplate.keys("addCategoryPage_*");
            redisTemplate.delete(keys);
            return "true";
        }
        return "false";
    }

    /**
     * 根据书籍种类id删除种类
     *
     * @param bookCategoryId
     * @return
     */
    @RequestMapping("/deleteCategory")
    @ResponseBody
    public String deleteBookCategoryById(@RequestParam("bookCategoryId") int bookCategoryId) {
        boolean res = bookCategoryService.removeById(bookCategoryId);
        if (res) {
            //清理掉所有该种类的缓存数据
            Set keys=redisTemplate.keys("addCategoryPage_*");
            redisTemplate.delete(keys);
            return "true";
        }
        return "false";
    }

}
