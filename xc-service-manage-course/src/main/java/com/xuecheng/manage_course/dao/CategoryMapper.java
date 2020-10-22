package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author 肖宏武
 * @date 2020/10/19 - 10:59
 */
@Component
@Mapper
public interface CategoryMapper {
    public CategoryNode findList();
}
