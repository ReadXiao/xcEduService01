package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.CategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 肖宏武
 * @date 2020/10/19 - 10:59
 */
@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    public CategoryNode findList() {
        CategoryNode list = categoryMapper.findList();
        return list;
    }
}
