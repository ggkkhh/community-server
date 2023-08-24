package com.roydon.common.enums;

/**
 * @author roydon
 * @date 2023-08-24 20:32【星期四】
 * @description com.roydon.common.enums
 * <p> 系统反馈平台枚举 </p>
 * 默认为 0 app
 **/
public enum FeedbackPlatformEnum {

    APP("0", "app"),
    WEB("1", "web"),
    ;

    private final String code;
    private final String info;

    FeedbackPlatformEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static FeedbackPlatformEnum findByCode(String code) {
        for (FeedbackPlatformEnum value : FeedbackPlatformEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
