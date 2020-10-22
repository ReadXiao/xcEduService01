package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 肖宏武
 * @date 2020/10/19 - 14:30
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    public SysDictionary findByDType(String dType);
}
