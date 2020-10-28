package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 肖宏武
 * @date 2020/10/28 - 9:22
 */
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {
}
