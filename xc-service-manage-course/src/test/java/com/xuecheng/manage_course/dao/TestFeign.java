package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_course.client.CmsPageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 肖宏武
 * @date 2020/11/4 - 9:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFeign {
    @Resource
    CmsPageClient cmsPageClient;    //接口代理对象，由Feign生成代理对象

    @Test
    public void TestFeign(){
        CmsPage cmsPage = cmsPageClient.findCmsPageById("5f50c34844639a26a4666009");
        System.out.println(cmsPage);
    }
}
