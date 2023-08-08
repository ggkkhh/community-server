package com.roydon.business.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.roydon.business.oss.service.OssService;
import com.roydon.business.oss.utils.OssUtil;
import com.roydon.common.constant.Constants;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.exception.file.InvalidExtensionException;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtils;
import com.roydon.common.utils.file.FileUploadUtils;
import com.roydon.common.utils.file.MimeTypeUtils;
import com.roydon.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource
    private ISysUserService userService;

    @Override
    public String uploadFile(MultipartFile file) {
        OSS ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
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
        //调用oss方法上传到阿里云第一个参数：Bucket名称第二个参数：上传到oss文件路径和文件名称第三个参数：上传文件输入流
        ossClient.putObject(BUCKET_NAME, fileName, inputStream);
        //把上传后把文件url返回
        //https://xppll.oss-cn-beijing.aliyuncs.com/01.jpg
        String url = "https://" + BUCKET_NAME + "." + END_POINT + "/" + fileName;
        //关闭OSSClient
        ossClient.shutdown();
        return url;
    }

    @Override
    public String uploadUserAvatar(Long userId, String userName, MultipartFile file) {
        String uploadFile = this.uploadFile(file, OssUtil.USER_AVATAR_FILE);
        // 删除原来用户头像文件
        if (StringUtils.isNotEmpty(uploadFile)) {
            SysUser sysUser = userService.getById(userId);
            this.deleteObject(sysUser.getAvatar());
        }
        return uploadFile;
    }

    @Override
    public String uploadNoticeFile(MultipartFile file) {
        return this.uploadFile(file, OssUtil.NOTICE_FILE);
    }

    @Override
    public String uploadGoodsImgFile(MultipartFile file) {
        return this.uploadFile(file, OssUtil.GOODS_FILE);
    }

    @Override
    public String uploadCommonFile(MultipartFile file) {
        return this.uploadFile(file, OssUtil.COMMON_FILE);
    }

    private String uploadFile(MultipartFile file, String folder) {
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
//        String fileName = file.getOriginalFilename();
        String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date()); // 将日期转换为字符串
        // 文件存储名称：日期+username+uuid
        String ossFileName = folder + datePath + "/" + SecurityUtils.getUsername() + "-" + UUID.randomUUID().toString().replaceAll("-", "") + "." + FileUploadUtils.getExtension(file);
        ossClient.putObject(BUCKET_NAME, ossFileName, inputStream);
        String url = Constants.HTTPS + BUCKET_NAME + "." + END_POINT + "/" + ossFileName;
        //关闭OSSClient
        ossClient.shutdown();
        return url;
    }

    private void deleteObject(String url) {
        OSS ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        String urlPrefix = "https://" + BUCKET_NAME + "." + END_POINT + "/";
        System.out.println("urlPrefix==================" + urlPrefix);
        System.out.println("url==================" + url);
        String replace = url.replace(urlPrefix, "");
        System.out.println("replace==================" + replace);
        ossClient.deleteObject(BUCKET_NAME, replace);
        ossClient.shutdown();
    }

}
