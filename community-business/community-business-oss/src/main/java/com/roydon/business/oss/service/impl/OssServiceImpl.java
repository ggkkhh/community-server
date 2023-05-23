package com.roydon.business.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.roydon.business.oss.service.OssService;
import com.roydon.business.oss.utils.OssUtil;
import com.roydon.common.exception.file.InvalidExtensionException;
import com.roydon.common.utils.file.FileUploadUtils;
import com.roydon.common.utils.file.MimeTypeUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.roydon.business.oss.config.AliyunOssProperties.*;

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

        String url = null;

        //创建OSSClient实例。
        OSS ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

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
        ossClient.putObject(BUCKET_NAME, fileName, inputStream);

        //把上传后把文件url返回
        //https://xppll.oss-cn-beijing.aliyuncs.com/01.jpg
        url = "https://" + BUCKET_NAME + "." + END_POINT + "/" + fileName;
        //关闭OSSClient
        ossClient.shutdown();

        return url;
    }

    @Override
    public String uploadUserAvatar(String userName, MultipartFile file) {
        OSS ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.IMAGE_EXTENSION);
        } catch (InvalidExtensionException e) {
            throw new RuntimeException(e);
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileName = file.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = sdf.format(new Date()); // 将日期转换为字符串
        // 文件存储名称
        String ossFileName = OssUtil.USER_AVATAR_FILE + datePath + OssUtil.USER_AVATAR_PREFIX + UUID.randomUUID().toString().replaceAll("-", "") + fileName + "." + FileUploadUtils.getExtension(file);
        ossClient.putObject(BUCKET_NAME, ossFileName, inputStream);
        String url = "https://" + BUCKET_NAME + "." + END_POINT + "/" + ossFileName;
        //关闭OSSClient
        ossClient.shutdown();
        return url;
    }
}
