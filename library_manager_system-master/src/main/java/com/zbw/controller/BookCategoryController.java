package com.zbw.controller;

import com.zbw.domain.BookCategory;
import com.zbw.service.BookCategoryService;
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
public class BookCategoryController {
    @Autowired
    private BookCategoryService bookCategoryService;

    @Autowired
    private RedisTemplate redisTemplate;
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
