package com.zbw;

import com.zbw.domain.BookCategory;
import com.zbw.mapper.BookCategoryMapper;
import com.zbw.service.IAdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookCategoryMapperTest {

    @Autowired
    private BookCategoryMapper bookCategoryMapper;

    @Autowired
    private IAdminService adminService;

    @Test
    public void tesrSelectByPageNum(){

        List<BookCategory> list=bookCategoryMapper.selectByPageNum(0,5);
        if(null!=list){
            for(BookCategory b:list){
                System.out.println(b.getCategoryId()+" "+b.getCategoryName());
            }
        }
    }

    @Test
    public void testSelectAllCount(){
        int n=bookCategoryMapper.selectAllCount();
        System.out.println(n);
    }


    @Test
    public void testAddBookCategory(){
        BookCategory bookCategory=new BookCategory();
        bookCategory.setCategoryId(99);
        bookCategory.setCategoryName("xxx");
        System.out.println(adminService.addBookCategory(bookCategory));

    }
}
