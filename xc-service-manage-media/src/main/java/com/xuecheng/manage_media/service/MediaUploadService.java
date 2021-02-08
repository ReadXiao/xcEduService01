package com.xuecheng.manage_media.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.api.search.EsCourseControllerApi;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.response.CheckChunkResult;
import com.xuecheng.framework.domain.media.response.MediaCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_media.config.RabbitMQConfig;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import jdk.management.resource.ResourceRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * @author 肖宏武
 * @date 2021/2/3 - 16:56
 */
@Service
public class MediaUploadService {
    @Resource
    MediaFileRepository mediaFileRepository;

    @Resource
    RabbitTemplate rabbitTemplate;

    @Value("${xc-service-manage-media.upload-location}")
    private String upload_location;

    @Value("${xc-service-manage-media.mq.routingkey-media-video}")
    public  String routingkey_media_video;

    //得到文件所属目录的路径
    private String getFileFolderPath(String fileMd5){
        return upload_location+fileMd5.substring(0,1)+"/"+fileMd5.substring(1,2)+"/"+fileMd5+"/";
    }
    //得到文件的路径
    private String getFilePath(String fileMd5,String fileExt){
        return upload_location+fileMd5.substring(0,1)+"/"+fileMd5.substring(1,2)+"/"+fileMd5+"/"+fileMd5+"."+fileExt;
    }

    //得到块文件所属目录的路径
    private String getChunkFileFolderPath(String fileMd5){
        return upload_location+fileMd5.substring(0,1)+"/"+fileMd5.substring(1,2)+"/"+fileMd5+"/chunk/";
    }

    /**
     *  文件上传前注册，检查文件是否存在
     *  根据文件md5得到文件路径 *
     *  规则：
     *  一级目录：md5的第一个字符
     *  二级目录：md5的第二个字符
     *  三级目录：md5
     *  文件名：md5+文件扩展名
     *  @param fileMd5 文件md5值
     *  @param fileExt 文件扩展名
     *  @return 文件路径 */
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        //1、检查文件在磁盘上是否存在
        //文件所属目录的路径
        String fileFolderPath = this.getFileFolderPath(fileMd5);
        //文件的路径
        String filePath = this.getFilePath(fileMd5, fileExt);
        File file = new File(filePath);
        //文件是否存在
        boolean exists = file.exists();

        //2、文件信息在mongodb中是否存在
        Optional<MediaFile> optional = mediaFileRepository.findById(fileMd5);
        if (exists && optional.isPresent()){
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
        }
        //做一些准备工作，检查一下文件所在目录是否存在，如果不存在则创建
        File fileFolder = new File(fileFolderPath);
        if (!fileFolder.exists()){
            fileFolder.mkdirs();
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //分块检查
    public CheckChunkResult checkChunk(String fileMd5, Integer chunk, Integer chunkSize) {
        //检查分快文件是否存在
        String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);
        //块文件
        File chunkFile = new File(chunkFileFolderPath+chunk);
        if (chunkFile.exists()){
            //块文件存在
            return new CheckChunkResult(CommonCode.SUCCESS, true);
        }else{
            //块文件不存在
            return new CheckChunkResult(CommonCode.SUCCESS, false);
        }
    }

    //上传分块
    public ResponseResult uploadChunk(MultipartFile file, String fileMd5, Integer chunk) {
        //检查分块目录是否存在，如果不在则自动创建
        String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);
        //得到分块文件路径
        String chunkFilePath = chunkFileFolderPath+chunk;
        File chunkFileFolder = new File(chunkFileFolderPath);
        //如果不存在则自动创建
        if (!chunkFileFolder.exists()){
            chunkFileFolder.mkdirs();
        }
        //得到上传文件输入流
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(new File(chunkFilePath));
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //合并文件
    public ResponseResult mergeChunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        //1、合并所有分块
        //得到分块文件所属目录
        String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);
        File chunkFileFolder = new File(chunkFileFolderPath);
        //分快文件列表
        File[] files = chunkFileFolder.listFiles();
        List<File> fileList = Arrays.asList(files);
        //创建一个合并文件
        String filePath = this.getFilePath(fileMd5, fileExt);
        File mergeFile = new File(filePath);
        //执行合并
        mergeFile = this.mergeFile(fileList, mergeFile);
        if (mergeFile == null){
            ExceptionCast.cast(MediaCode.MERGE_FILE_FAIL);
        }
        //2、校验文件的md5值是否和前端传入的md5一样
        boolean checkFileMd5 = this.checkFileMd5(mergeFile, fileMd5);
        if (!checkFileMd5){
            //检验文件失败
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }
        //3、将文件的信息写mongodb
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileId(fileMd5);
        mediaFile.setFileOriginalName(fileName);
        mediaFile.setFileName(fileMd5+"."+fileExt);
        //保存文件相对路径
        String filePath1 = fileMd5.substring(0,1)+"/"+fileMd5.substring(1,2)+"/"+fileMd5+"/";
        mediaFile.setFilePath(filePath1);
        mediaFile.setFileSize(fileSize);
        mediaFile.setUploadTime(new Date());
        mediaFile.setMimeType(mimetype);
        mediaFile.setFileType(fileExt);
        //状态为上传成功
        mediaFile.setFileStatus("301002");
        MediaFile save = mediaFileRepository.save(mediaFile);
        //向mq发送视频处理信息
        sendProcessVideoMsg(mediaFile.getFileId());
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //发送视频处理消息
    public ResponseResult sendProcessVideoMsg(String mediaId){
        //查询数据库mediaFile
        Optional<MediaFile> optional = mediaFileRepository.findById(mediaId);
        if (!optional.isPresent()){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //构建消息内容
        HashMap<String, String> map = new HashMap<>();
        map.put("mediaId",mediaId);
        String jsonString = JSON.toJSONString(map);
        //向mq发送消息
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EX_MEDIA_PROCESSTASK,routingkey_media_video,jsonString);
        } catch (AmqpException e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //合并文件
    private File mergeFile(List<File> chunkFileList,File mergeFile){
        //如果合并文件已存在则删除，否则创建文件
        try {
            if (mergeFile.exists()){
                mergeFile.delete();
            }else{
                mergeFile.createNewFile();
            }

            //对块文件排序
            Collections.sort(chunkFileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if (Integer.parseInt(o1.getName())>Integer.parseInt(o2.getName())){
                        return 1;
                    }
                    return -1;
                }
            });

            //创建一个写对象
            RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
            byte[] b = new byte[1024];
            for (File chunkFile : chunkFileList) {
                RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "rw");
                int len = -1;
                while((len = raf_read.read(b))!=-1){
                    raf_write.write(b,0,len);
                }
                raf_read.close();
            }
            raf_write.close();
            return mergeFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //检验文件
    private boolean checkFileMd5(File mergeFile,String md5){
        //创建文件输入流
        try {
            FileInputStream inputStream = new FileInputStream(mergeFile);
            //得到文件的MD5
            String md5Hex = DigestUtils.md5Hex(inputStream);
            //和传入的md5比较
            if (md5.equalsIgnoreCase(md5Hex)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
