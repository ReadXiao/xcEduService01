package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 肖宏武
 * @date 2020/10/19 - 15:40
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {
    @Resource
    private CategoryService categoryService;

    @Test
    public void findListTest(){
        CategoryNode list = categoryService.findList();
        System.out.println("======================================================"+list);
    }
}
