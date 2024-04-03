package com.zbw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbw.domain.Book;
import com.zbw.domain.BorrowingBooks;
import com.zbw.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BorrowingBooksMapper extends BaseMapper<BorrowingBooks> {

    @Select("select *\n" +
            "from borrowingbooks\n" +
            "limit #{currIndex},#{pageSize}")
    // 分页查询所有记录
    List<BorrowingBooks> selectAllByPage(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    @Select("select * from book where book_id=#{id}")
    //根据id获取book的信息
    Book selectBookById(@Param("id") int id);

    //根据id获取User的信息
    @Select("select * from user where user_id=#{id}")
        //根据id获取book的信息
    User selectUserById(@Param("id") int id);

}