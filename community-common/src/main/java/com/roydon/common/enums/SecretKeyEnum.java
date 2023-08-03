package com.roydon.common.enums;

/**
 * @author roydon
 * @date 2023-08-03 12:17【星期四】
 * @description com.roydon.common.enums
 * <p> 密钥来源枚举 </p>
 **/
public enum SecretKeyEnum {

    // TODO 密钥表每增加一个密钥在这里手动添加
    ALIYUN(1, "aliyun"),

    ROLL(2, "roll");

    private final int code;
    private final String info;

    SecretKeyEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
