package com.zbw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbw.domain.*;
import com.zbw.mapper.AdminMapper;
import com.zbw.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {

    @Autowired
    private AdminService adminService;


    /**
     * 判断是否为正确的账户，是则返回对应admin对象，否则返回null
     * @param name
     * @param password
     * @return
     */
    @Override
    public Admin adminLogin(String name, String password) {
        //可能会有重名的人，所以要查询多条数据
        List<Admin> admin = query().eq("admin_name", name).list();
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
        boolean n = adminService.updateById(admin);
        if (n) {
            //修改成功，更新session对象
            Admin newAdmin = query().eq("admin_id", admin.getAdminId()).one();
            request.getSession().setAttribute("admin", newAdmin);
            return true;
        }
        return false;
    }
}
