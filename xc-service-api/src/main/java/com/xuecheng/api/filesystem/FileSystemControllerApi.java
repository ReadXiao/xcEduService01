package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 肖宏武
 * @date 2020/10/28 - 9:14
 */
@Api(value = "文件管理接口",description = "文件管理接口，提供文件的增删改查")
public interface FileSystemControllerApi {
    //上传文件
    @ApiOperation("上传文件接口")
    public UploadFileResult upload(MultipartFile multipartFile,String filetag,String businesskey,@ApiParam("json格式数据") String metadata);
}
