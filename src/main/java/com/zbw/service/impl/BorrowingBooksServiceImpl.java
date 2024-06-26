package com.zbw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbw.domain.Book;
import com.zbw.domain.BorrowingBooks;
import com.zbw.domain.User;
import com.zbw.domain.Vo.BorrowingBooksVo;
import com.zbw.mapper.BorrowingBooksMapper;
import com.zbw.service.BorrowingBooksService;
import com.zbw.utils.page.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BorrowingBooksServiceImpl extends ServiceImpl<BorrowingBooksMapper, BorrowingBooks> implements BorrowingBooksService {

    /**
     * 用户还书
     * @param bookId
     * @param request
     * @return
     */
    @Override
    public boolean userReturnBook(int bookId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
//        QueryChainWrapper<BorrowingBooks> queryChainWrapper =
//                this.query().eq("user_id", user.getUserId()).eq("book_id", bookId);
//        boolean n = this.remove(queryChainWrapper);
        QueryWrapper<BorrowingBooks> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId()).eq("book_id", bookId);
        boolean n = this.remove(queryWrapper);
//        LambdaQueryWrapper<BorrowingBooks> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(BorrowingBooks::getUserId, user.getUserId())
//                .eq(BorrowingBooks::getBookId, bookId);
//        boolean n = this.remove(queryWrapper);
        return n;
    }

    /**
     * 查询对应的借书信息
     * @param pageNum
     * @return
     */
    @Override
    public Page<BorrowingBooksVo> selectAllByPage(int pageNum) {
        //查询10条数据
        List<BorrowingBooks> list = getBaseMapper().selectAllByPage((pageNum - 1) * 10, 10);
        if (null == list) {
            return null;
        }
        Page<BorrowingBooksVo> page = new Page<BorrowingBooksVo>();
        List<BorrowingBooksVo> borrowingBooksVos = new LinkedList<>();
        for (BorrowingBooks b : list) {
            User user = getBaseMapper().selectUserById(b.getUserId());
            Book book = getBaseMapper().selectBookById(b.getBookId());
            BorrowingBooksVo borrowingBooksVo = new BorrowingBooksVo();

            borrowingBooksVo.setUser(user);
            borrowingBooksVo.setBook(book);

            //日期转换
            Date date1 = b.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfBorrowing = sdf.format(date1);

            //算出还书日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, 2);//增加两个月
            Date date2 = calendar.getTime();
            String dateOfReturn = sdf.format(date2);

            borrowingBooksVo.setDateOfBorrowing(dateOfBorrowing);
            borrowingBooksVo.setDateOfReturn(dateOfReturn);
            borrowingBooksVos.add(borrowingBooksVo);
        }



        page.setList(borrowingBooksVos);
        page.setPageNum(pageNum);
        page.setPageSize(10);

        //查找总页数
        int recordCount = this.count();//总和记录
        //计算页数
        int pageCount = recordCount / 10;
        if (recordCount % 10 != 0) {
            pageCount++;
        }
        page.setPageCount(pageCount);
        return page;
    }

    /**
     *查询用户借书记录并返回一个集合
     * @param request
     * @return
     */
    @Override
    public ArrayList<BorrowingBooksVo> selectAllBorrowRecord(HttpServletRequest request) {
        //提取当前用户对象
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return null;
        }
        //数据库查询用户借书记录
        List<BorrowingBooks> list = this.query().eq("user_id", user.getUserId()).list();
        if (null == list) {
            return null;
        }
        //创建BorrowingBooksVo类型的集合，并为它注入属性
        ArrayList<BorrowingBooksVo> borrowingBooksVos = new ArrayList<>();
        for (BorrowingBooks b : list) {
            Book book = getBaseMapper().selectBookById(b.getBookId());
            BorrowingBooksVo borrowingBooksVo = new BorrowingBooksVo();

            borrowingBooksVo.setBook(book);
            //日期转换
            Date date1 = b.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfBorrowing = sdf.format(date1);

            //算出还书日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, 2);//增加两个月
            Date date2 = calendar.getTime();
            String dateOfReturn = sdf.format(date2);

            borrowingBooksVo.setDateOfBorrowing(dateOfBorrowing);
            borrowingBooksVo.setDateOfReturn(dateOfReturn);

            borrowingBooksVos.add(borrowingBooksVo);
        }

        return borrowingBooksVos;
    }
}
