package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 肖宏武
 * @date 2020/10/28 - 9:23
 */
@Service
public class FileSystemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemService.class);

    @Value("${xuecheng.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.charset}")
    String charset;

    @Resource
    private FileSystemRepository fileSystemRepository;
    //上传文件
    public UploadFileResult upload(MultipartFile multipartFile,
                                   String filetag,
                                   String businesskey,
                                   String metadata){
        //第一步：将文件上传到fastdfs中，得到一个文件id
        String fileId = fdfs_upload(multipartFile);
        if (StringUtils.isEmpty(fileId)){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //第二步：将文件id及其他文件信息存储到mongodb
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFiletag(filetag);
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());
        if (StringUtils.isNotEmpty(metadata)){
            Map map = null;
            try {
                map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
    }

    private String fdfs_upload(MultipartFile multipartFile){
        try {
            //初始化fastDFS环境
            //ClientGlobal.initByProperties("fastdfs-client.properties");
            initFdfsConfig();
            //创建一个trackerClient
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //得到storage服务器
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建storageClient来上传文件
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            //上传文件
            //得到文件字节
            byte[] bytes = multipartFile.getBytes();
            //得到文件原始名称
            String originalFilename = multipartFile.getOriginalFilename();
            //得到文件扩展名
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileId = storageClient1.upload_file1(bytes, ext, null);
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //初始化fastDFS环境
    private void initFdfsConfig(){
        //初始化tracker服务地址（多个tracker中间以半角逗号分隔）
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
        } catch (Exception e) {
            e.printStackTrace();
            //抛出异常
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }
    }
}
