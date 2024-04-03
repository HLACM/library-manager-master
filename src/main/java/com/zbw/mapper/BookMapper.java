package com.zbw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbw.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

    @Select("select *\n" +
            "from book\n" +
            "where book_category = #{categoryId}\n" +
            "limit #{currIndex},#{pageSize}")
    //按书籍种类分页查找
    List<Book> selectByCategoryId(@Param("categoryId") int categoryId, @Param("currIndex") int currIndex, @Param("pageSize") int PageSize);


}