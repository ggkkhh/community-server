package com.roydon.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @USER: roydon
 * @DATE: 2023/5/9 10:04
 * @Description 手机验证码登录
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsLoginBody {
    private String telephone;
    private String phoneCode;
}
