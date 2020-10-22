package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.SysDictionaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.service.SysDictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 肖宏武
 * @date 2020/10/18 - 9:54
 */
@RestController
@RequestMapping("/sys")
public class SysDictionaryController implements SysDictionaryControllerApi {
    @Resource
    private SysDictionaryService sysDictionaryService;

    @GetMapping("/dictionary/get/{dType}")
    @Override
    public SysDictionary getByType(@PathVariable("dType") String dType) {
        SysDictionary byType = sysDictionaryService.getByType(dType);
        return byType;
    }
}
