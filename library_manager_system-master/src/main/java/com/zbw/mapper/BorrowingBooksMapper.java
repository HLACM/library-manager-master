package com.zbw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbw.domain.Admin;
import com.zbw.domain.BorrowingBooks;
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
}