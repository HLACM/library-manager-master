package com.zbw;

import com.zbw.domain.Admin;
import com.zbw.domain.BookCategory;
import com.zbw.mapper.BookCategoryMapper;
import com.zbw.service.AdminService;
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

    @Resource
    private BookCategoryMapper bookCategoryMapper;

    @Resource
    private AdminService adminService;

    @Test
    public void login(){
        Admin admin = adminService.adminLogin("admin", "123456");
        System.out.println(admin.toString());
    }




    @Test
    public void tesrSelectByPageNum(){

        List<BookCategory> list=bookCategoryMapper.selectByPageNum(0,5);
        if(null!=list){
            for(BookCategory b:list){
                System.out.println(b.getCategoryId()+" "+b.getCategoryName());
            }
        }
    }





}
