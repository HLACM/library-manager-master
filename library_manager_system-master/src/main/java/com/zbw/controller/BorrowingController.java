package com.zbw.controller;

import com.zbw.domain.Vo.BookVo;
import com.zbw.domain.Vo.BorrowingBooksVo;
import com.zbw.service.IBorrowingBooksRecordService;
import com.zbw.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Controller
public class BorrowingController {

    @Autowired
    private IBorrowingBooksRecordService borrowingBooksRecordService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 返回所有用户借书记录页面
     * 使用Redis缓存技术
     * @return
     */
    @RequestMapping("/allBorrowBooksRecordPage")
    public String allBorrowingBooksRecordPage(Model model, @RequestParam("pageNum") int pageNum) {
        Page<BorrowingBooksVo> page=null;
        //用pageNum动态获取键名,pageNum代表第几页
        String key="allBorrowBooksRecordPage"+"_"+pageNum;
        page=(Page<BorrowingBooksVo>)redisTemplate.opsForValue().get(key);
        if (page!=null){
            model.addAttribute("page", page);
            return "admin/allBorrowingBooksRecord";
        }
        //未查询到缓存则封装好查询出来的数据并存入Redis
        page = borrowingBooksRecordService.selectAllByPage(pageNum);
        redisTemplate.opsForValue().set(key,page,30, TimeUnit.MINUTES);
        model.addAttribute("page", page);
        return "admin/allBorrowingBooksRecord";
    }
}
