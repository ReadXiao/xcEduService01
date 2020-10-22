package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 肖宏武
 * @date 2020/10/17 - 16:09
 */
@Api(value = "课程分类管理",description = "课程分类管理",tags = "课程分类管理")
public interface CategoryControllerApi {

    @ApiOperation("查询分类")
    public CategoryNode findList();
}
