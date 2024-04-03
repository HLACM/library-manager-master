package com.zbw;

import com.zbw.domain.BorrowingBooks;
import com.zbw.mapper.BorrowingBooksMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowingBooksMapperTest {

    @Resource
    private BorrowingBooksMapper borrowingBooksMapper;



}
