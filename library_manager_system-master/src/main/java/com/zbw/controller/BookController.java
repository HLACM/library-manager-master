package com.zbw.controller;

import com.zbw.domain.Book;
import com.zbw.domain.BookCategory;
import com.zbw.domain.Vo.BookVo;
import com.zbw.service.IAdminService;
import com.zbw.service.IBookCategoryService;
import com.zbw.service.IBookService;
import com.zbw.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IBookService bookService;
    @Autowired
    private IBookCategoryService bookCategoryService;

    /**
     * 管理员&emsp;&emsp;录入新书
     *
     * @param book
     * @return
     */
    @RequestMapping("/addBook")
    @ResponseBody
    public String addBook(Book book) {
        //方法返回一个布尔值
        boolean res = adminService.addBook(book);
        if (res) {
            return "true";
        }
        return "false";
    }

    /**
     * 返回管理员查询书籍结果页
     *
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/showBooksResultPageByCategoryId")
    public String showBooksResultPageByCategoryId(@RequestParam("pageNum") int pageNum, @RequestParam("bookCategory") int bookCategory, Model model) {
        //封装好查询出来的数据
        Page<BookVo> page = bookService.findBooksByCategoryId(bookCategory, pageNum);
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
        //参数为对应书籍的名字，通过名字查找对应的数据。由于展示的是一条数据所以不需要分页
        List<BookVo> bookVos = bookService.selectBooksByBookPartInfo(bookPartInfo);
        model.addAttribute("bookList", bookVos);
        return "user/findBook";
    }

    /**
     * 查询所有书籍种类（新建类别页面中展示）
     *
     * @return
     */
    @RequestMapping("/findAllBookCategory")
    @ResponseBody
    public List<BookCategory> findAllBookCategory() {
        return adminService.getBookCategories();
    }

    /**
     * 新建书籍种类
     *
     * @param bookCategory
     * @return
     */
    @RequestMapping("/addBookCategory")
    @ResponseBody
    public String addBookCategory(BookCategory bookCategory) {
        boolean b = adminService.addBookCategory(bookCategory);
        if (b) {
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
        int res = bookCategoryService.deleteBookCategoryById(bookCategoryId);
        if (res > 0) {
            return "true";
        }
        return "false";
    }

}
