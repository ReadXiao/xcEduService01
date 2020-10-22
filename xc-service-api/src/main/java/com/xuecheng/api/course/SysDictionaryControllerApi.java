package com.xuecheng.api.course;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.ApiOperation;

/**
 * @author 肖宏武
 * @date 2020/10/17 - 20:19
 */
public interface SysDictionaryControllerApi {
    @ApiOperation(value = "数据字典查询接口")
    public SysDictionary getByType(String dType);
}
