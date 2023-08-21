package com.roydon.business.epidemic.enums;

/**
 * @author roydon
 * @date 2023-08-21 21:03【星期一】
 * @description com.roydon.business.epidemic.enums
 * <p> 进出社区类型 </p>
 **/
public enum AccessTypeEnum {

    OUT("0", "出"), IN("1", "进");

    private final String code;
    private final String info;

    AccessTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static AccessTypeEnum findByCode(String code) {
        for (AccessTypeEnum value : AccessTypeEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
