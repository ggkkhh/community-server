package com.roydon.sms.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roydon.sms.domain.entity.SmsProvider;
import com.roydon.sms.service.ISmsProviderService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * project : community-server
 * <p> 阿里短信服务配置key与security </p>
 *
 * @author roydon
 * @date 2023-07-20【星期四】
 **/
@Component
@ConfigurationProperties(prefix = "sms.aliyun")
public class AliyunSmsProperties implements InitializingBean {

    @Resource
    private ISmsProviderService smsProviderService;

    public static String SMS_ACCESS_KEY_ID;
    public static String SMS_ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        LambdaQueryWrapper<SmsProvider> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SmsProvider::getProviderName,"阿里云");
        SmsProvider one = smsProviderService.getOne(queryWrapper);
        SMS_ACCESS_KEY_ID = one.getAccessKeyId();
        SMS_ACCESS_KEY_SECRET = one.getAccessKeySecret();
    }
}
