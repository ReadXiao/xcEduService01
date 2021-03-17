package com.xuecheng.learning.dao;

import com.xuecheng.framework.domain.learning.XcLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 肖宏武
 * @date 2021/2/28 - 21:21
 */
public interface XcLearningCourseRepository extends JpaRepository<XcLearningCourse,String> {
    //根据用户id和课程id查询
    XcLearningCourse findByUserIdAndCourseId(String userId,String courseId);
}
