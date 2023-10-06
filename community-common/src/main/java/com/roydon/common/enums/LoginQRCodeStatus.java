package com.roydon.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * LoginQRCodeStatus
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/6
 * 登录二维码状态
 **/
@Getter
@AllArgsConstructor
public enum LoginQRCodeStatus {

    WAITING("0", "待扫描"),

    SCANNED("1", "待确认"),

    CONFIRMED("2", "已确认"),

    INVALID("-1", "已失效"),
    ;

    private final String code;
    private final String info;

    public static LoginQRCodeStatus findByCode(String code) {
        for (LoginQRCodeStatus value : LoginQRCodeStatus.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
