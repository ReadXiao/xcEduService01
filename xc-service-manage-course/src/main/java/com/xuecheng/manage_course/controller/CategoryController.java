package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.service.CategoryService;
import com.xuecheng.manage_course.service.SysDictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 肖宏武
 * @date 2020/10/18 - 9:56
 */
@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    @Override
    public CategoryNode findList() {
        CategoryNode categoryNode = categoryService.findList();
        return categoryNode;
    }
}
