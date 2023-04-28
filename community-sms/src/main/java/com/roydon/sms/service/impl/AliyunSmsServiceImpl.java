package com.roydon.sms.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.exception.alisms.SmsException;
import com.roydon.common.utils.StringUtils;
import com.roydon.sms.domain.model.AliSmsResponse;
import com.roydon.sms.domain.model.SmsCode;
import com.roydon.sms.service.AliyunSmsService;
import com.roydon.sms.util.AliyunSmsUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * AliyunSmsServiceImpl
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/27
 **/
@Service
public class AliyunSmsServiceImpl implements AliyunSmsService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static final long SMSCODE_EXPIRE_TIME = 1l;

    @Override
    public SmsCode sendCode(String phone) {
        // 根据手机号从redis中拿验证码
        String phoneCode = redisTemplate.opsForValue().get(CacheConstants.ALIYUN_SMS_KEY + phone);
        if (!StringUtils.isEmpty(phoneCode)) {
            throw new SmsException("请勿重复发送-" + phone);
        }
        // 生成随机验证码
        String smsCode = RandomStringUtils.randomNumeric(6);

        String callbackMsg = AliyunSmsUtil.sendSms(phone, 7, smsCode);
        // 回调消息转换
        JSONObject jsonObject = JSON.parseObject(callbackMsg);
        AliSmsResponse aliSmsResponse = JSON.toJavaObject(jsonObject, AliSmsResponse.class);
        System.out.println(aliSmsResponse.toString());
        // 调用短信服务失败
        if (!aliSmsResponse.getCode().equals(AliyunSmsUtil.MESSAGE_OK)) {
            throw new SmsException(aliSmsResponse.getMessage());
        }
        // 将验证码存到 redis
        redisTemplate.opsForValue().set(CacheConstants.ALIYUN_SMS_KEY + phone, smsCode, SMSCODE_EXPIRE_TIME, TimeUnit.MINUTES);
        SmsCode sCode = new SmsCode(smsCode, SMSCODE_EXPIRE_TIME);
        return sCode;
    }
}
