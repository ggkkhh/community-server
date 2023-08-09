package com.roydon.business.epidemic.enums;

/**
 * @author roydon
 * @date 2023-08-08 22:33【星期二】
 * @description com.roydon.business.epidemic.enums
 * <p> 健康码状态 </p>
 **/
public enum CodeStatusEnum {

    GREEN("0", "绿"), YELLOW("1", "黄"), RED("2", "红");

    private final String code;
    private final String info;

    CodeStatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static CodeStatusEnum findByCode(String code) {
        for (CodeStatusEnum value : CodeStatusEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
