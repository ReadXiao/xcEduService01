package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 肖宏武
 * @date 2020/10/19 - 15:49
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CourseServiceTest {
    @Resource
    private CourseService courseService;

    @Test
    public void getCourseMarketById(){
        String courseId = "4028e581617f945f01617f9dabc40000";
        CourseMarket courseMarketById = courseService.getCourseMarketById(courseId);
        log.info("result:{}",courseMarketById);
    }

    @Test
    public void updateCourseMarketTest(){
        CourseMarket courseMarket = new CourseMarket();
        courseMarket.setQq("1392016499");
        String courseId = "4028e581617f945f01617f9dabc40000";
        ResponseResult responseResult = courseService.updateCourseMarket(courseId, courseMarket);
        log.info("result:{}",responseResult);
    }

    @Test
    public void getCourseBaseById(){
        String courseId = "4028e581617f945f01617f9dabc40000";
        CourseBase courseBaseById = courseService.getCourseBaseById(courseId);
        log.info("result:{}",courseBaseById);
    }

    @Test
    public void updateCourseBase(){
        String courseId = "40288581625c7e7301625c8ed6af0000";
        CourseBase courseBase = new CourseBase();
        courseBase.setName("测试001");
        ResponseResult responseResult = courseService.updateCourseBase(courseId, courseBase);
        System.out.println("========================================"+responseResult);
    }

    @Test
    public void findCourseList(){
        int page = 1;
        int size = 5;
        String courseId = "1";
        CourseListRequest courseListRequest = new CourseListRequest();
        QueryResponseResult<CourseInfo> courseList = courseService.findCourseList(courseId,page, size, courseListRequest);
        System.out.println("==============================================================="+courseList);
    }
}
