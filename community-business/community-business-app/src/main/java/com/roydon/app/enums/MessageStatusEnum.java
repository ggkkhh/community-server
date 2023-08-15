package com.roydon.app.enums;

/**
 * @author roydon
 * @date 2023-08-15 20:20【星期二】
 * @description com.roydon.business.epidemic.enums
 * <p> 消息状态0未读1已读 </p>
 **/
public enum MessageStatusEnum {

    NOT_READ("0", "未读"), READ("1", "已读");

    private final String code;
    private final String info;

    MessageStatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static MessageStatusEnum findByCode(String code) {
        for (MessageStatusEnum value : MessageStatusEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
