package com.zbw.service.impl;

import com.zbw.domain.*;
import com.zbw.mapper.AdminMapper;
import com.zbw.mapper.BookCategoryMapper;
import com.zbw.mapper.BookMapper;
import com.zbw.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookCategoryMapper bookCategoryMapper;


    /**
     * 判断是否为正确的账户，是则返回对应admin对象，否则返回null
     * @param name
     * @param password
     * @return
     */
    @Override
    public Admin adminLogin(String name, String password) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        //添加adiminName等于形参name的条件
        criteria.andAdminNameEqualTo(name);
        //按条件查询对应的对象
        List<Admin> admin = adminMapper.selectByExample(adminExample);
        if (null == admin) {
            return null;
        }
        for (Admin a : admin) {
            if (a.getAdminPwd().equals(password)) {
                //密码对应正确则返回admin对象
                return a;
            }
        }
        return null;
    }

    @Override
    public boolean addBook(Book book) {
        int n = bookMapper.insert(book);
        if (n > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<BookCategory> getBookCategories() {
        BookCategoryExample bookCategoryExample = new BookCategoryExample();
        return bookCategoryMapper.selectByExample(bookCategoryExample);
    }

    @Override
    public boolean addBookCategory(BookCategory bookCategory) {
        int n = bookCategoryMapper.insert(bookCategory);
        if (n > 0) {
            return true;
        }
        return false;
    }

    /**
     * 修改对应的管理员信息
     * @param admin
     * @param request
     * @return
     */
    @Override
    public boolean updateAdmin(Admin admin, HttpServletRequest request) {
        //获取session对象中admin对象
        Admin sessionAdmin = (Admin) request.getSession().getAttribute("admin");
        admin.setAdminId(sessionAdmin.getAdminId());
        //按主键id进行数据的更新
        int n = adminMapper.updateByPrimaryKey(admin);

        if (n > 0) {
            //修改成功，更新session对象
            Admin newAdmin = adminMapper.selectByPrimaryKey(admin.getAdminId());
            request.getSession().setAttribute("admin", newAdmin);
            return true;
        }

        return false;
    }
}
