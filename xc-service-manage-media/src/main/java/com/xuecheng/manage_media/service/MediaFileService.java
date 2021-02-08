package com.xuecheng.manage_media.service;

import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 肖宏武
 * @date 2021/2/4 - 20:03
 */
@Service
public class MediaFileService {
    private static Logger logger = LoggerFactory.getLogger(MediaFileService.class);

    @Autowired
    MediaFileRepository mediaFileRepository;

    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        if (queryMediaFileRequest == null){
            queryMediaFileRequest = new QueryMediaFileRequest();
        }

        //查询条件
        MediaFile mediaFile = new MediaFile();
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getTag())){
            mediaFile.setTag(queryMediaFileRequest.getTag());
        }
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())){
            mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
        }
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())){
            mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
        }
        //查询条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains())//tag字段 模糊匹配
                .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains())//文件原始名称模糊匹配
                .withMatcher("processStatus", ExampleMatcher.GenericPropertyMatchers.exact());// 处理状态精确匹配（默认）
        //定义example条件对象
        Example<MediaFile> example = Example.of(mediaFile, exampleMatcher);
        //分页查询对象
        if (page<=0){
            page = 1;
        }
        if (size<=0){
            size = 10;
        }
        PageRequest pageRequest = new PageRequest(page-1, size);
        Page<MediaFile> all = mediaFileRepository.findAll(example, pageRequest);
        //总记录数
        long total = all.getTotalElements();
        //数据列表
        List<MediaFile> content = all.getContent();
        QueryResult<MediaFile> queryResult = new QueryResult<>();
        queryResult.setList(content);
        queryResult.setTotal(total);

        QueryResponseResult<MediaFile> queryResponseResult = new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }
}
