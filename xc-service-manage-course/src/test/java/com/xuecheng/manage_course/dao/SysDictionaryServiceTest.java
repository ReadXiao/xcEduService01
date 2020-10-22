package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.service.SysDictionaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 肖宏武
 * @date 2020/10/19 - 14:46
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysDictionaryServiceTest {
    @Resource
    private SysDictionaryService sysDictionaryService;

    @Resource
    private SysDictionaryRepository sysDictionaryRepository;

    @Test
    public void getByTypeTest(){
        String dType = "201";
        SysDictionary byDType = sysDictionaryService.getByType(dType);
        System.out.println(byDType);
    }
}
