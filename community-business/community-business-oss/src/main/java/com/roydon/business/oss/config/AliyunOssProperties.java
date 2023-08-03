package com.roydon.business.oss.config;

import com.roydon.common.enums.SecretKeyEnum;
import com.roydon.system.domain.SysSecret;
import com.roydon.system.service.ISysSecretService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ConfigurationProperties(prefix = "oss.aliyun")
public class AliyunOssProperties implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    @Resource
    private ISysSecretService sysSecretService;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        SysSecret sysSecret = sysSecretService.selectOneBySecretKey(SecretKeyEnum.ALIYUN.getInfo());
        END_POINT = endpoint;
        ACCESS_KEY_ID = sysSecret.getKeyId();
        ACCESS_KEY_SECRET = sysSecret.getKeySecret();
        BUCKET_NAME = bucketName;
    }
}
