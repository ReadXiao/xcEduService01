package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.dao.SysDictionaryRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 肖宏武
 * @date 2020/10/19 - 14:32
 */
@Service
public class SysDictionaryService {
    @Resource
    private SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary getByType(String dType) {
        SysDictionary sysDictionary = sysDictionaryRepository.findByDType(dType);
        return sysDictionary;
    }
}
