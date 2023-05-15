package com.roydon.sms.service;

import com.roydon.sms.domain.model.AliSmsResponse;
import com.roydon.sms.domain.model.SmsCode;

/**
 * AlismsService
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/27
 **/
public interface AliyunSmsService {

    /**
     * 手机发送验证码
     *
     * @param phone phone
     * @return ali回调返回体封装
     */
    SmsCode sendCode(String phone);
}
