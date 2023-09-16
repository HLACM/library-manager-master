package com.zbw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbw.domain.Admin;
import com.zbw.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select *\n" +
            "from user\n" +
            "limit #{currIndex},#{pageSize}")
    //分页查询
    List<User> selectByPageNum(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);
}