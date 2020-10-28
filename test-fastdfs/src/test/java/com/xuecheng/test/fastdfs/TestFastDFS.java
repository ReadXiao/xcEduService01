package com.xuecheng.test.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 肖宏武
 * @date 2020/10/23 - 15:34
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFastDFS {
    //上传测试
    @Test
    public void testUpload(){
        try {
            //加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义TrackerClient,用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建一个storageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            //上传文件
            //本地文件路径
            String filePath = "C:/Users/HP/Desktop/timg.jpg";
            //上传成功后获得文件id
            String fileId = storageClient1.upload_file1(filePath, "jpg", null);
            //group1/M00/00/00/wKhViV-SnIGAHvXnAABMFP-G5jI405.jpg   centos虚拟机
            //group1/M00/00/00/rBRrr1-WJmOAXhtFAABMFP-G5jI294.jpg  阿里云服务器
            //group1/M00/00/00/rBRrr1-WJwKAHDNEAABMFP-G5jI523.jpg  阿里云服务器
            //group1/M00/00/00/rBRrr1-ZHWaAETBiAAJnMM1OJAQ036.png
            //group1/M00/00/00/rBRrr1-ZH8-ADfFuAAJnMM1OJAQ456.png
            //http://120.26.176.162/group1/M00/00/00/rBRrr1-WLXWAC4odAABMFP-G5jI519.jpg 阿里云服务器
            System.out.println(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //下载测试
    @Test
    public void testDownload(){
        try {
            //加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义TrackerClient,用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建一个storageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);

            //下载
            String fileId = "group1/M00/00/00/rBRrr1-WJmOAXhtFAABMFP-G5jI294.jpg";
            byte[] bytes = storageClient1.download_file1(fileId);
            FileOutputStream outputStream = new FileOutputStream(new File("d:/logo.jpg"));
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void QueryTest(){
        try {
            //加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义TrackerClient,用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建一个storageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);

            //查询
            String fileId = "group1/M00/00/00/rBRrr1-WJwKAHDNEAABMFP-G5jI523.jpg";
            FileInfo fileInfo = storageClient1.query_file_info1(fileId);
            System.out.println(fileInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
