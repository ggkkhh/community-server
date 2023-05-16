package com.roydon.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRegisterBody
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterBody {
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 验证码
     */
    private String code;
}
