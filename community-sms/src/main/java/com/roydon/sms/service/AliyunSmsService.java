package com.roydon.sms.service;

import com.roydon.sms.domain.model.AliSmsResponse;

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
    AliSmsResponse sendCode(String phone);
}
