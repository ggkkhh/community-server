package com.roydon.business.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.roydon.business.oss.config.AliyunOssProperties;
import com.roydon.business.oss.service.OssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * OssServiceImpl
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/15
 **/
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFile(MultipartFile file) {

        String endpoint = AliyunOssProperties.END_POINT;
        String accessKeyId = AliyunOssProperties.ACCESS_KEY_ID;
        String accessKeySecret = AliyunOssProperties.ACCESS_KEY_SECRET;
        String bucketName = AliyunOssProperties.BUCKET_NAME;
        String url = null;

        //创建OSSClient实例。
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        //获取上传文件输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //获取文件名称
        String fileName = file.getOriginalFilename();

        //保证文件名唯一，去掉uuid中的'-'
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = uuid + fileName;

        //把文件按日期分类，构建日期路径：avatar/2000/02/26/文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // 定义日期格式
        String datePath = sdf.format(new Date()); // 将日期转换为字符串
        //拼接
        fileName = datePath + "/" + fileName;

        //调用oss方法上传到阿里云
        //第一个参数：Bucket名称
        //第二个参数：上传到oss文件路径和文件名称
        //第三个参数：上传文件输入流
        ossClient.putObject(bucketName, fileName, inputStream);

        //把上传后把文件url返回
        //https://xppll.oss-cn-beijing.aliyuncs.com/01.jpg
        url = "https://" + bucketName + "." + endpoint + "/" + fileName;
        //关闭OSSClient
        ossClient.shutdown();

        return url;
    }
}
