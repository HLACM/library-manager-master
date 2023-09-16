package com.zbw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbw.domain.Admin;
import com.zbw.domain.BookCategory;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookCategoryMapper extends BaseMapper<BookCategory> {

    @Select("select *\n" +
            "from book_category\n" +
            "limit #{currIndex},#{pageSize}")
    //分页查询
    List<BookCategory> selectByPageNum(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

}