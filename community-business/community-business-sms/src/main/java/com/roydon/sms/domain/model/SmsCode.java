package com.roydon.sms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @USER: roydon
 * @DATE: 2023/4/28 14:36
 * @Description SmsCode
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsCode {
    private String code;
    private Long expireTime;
}
