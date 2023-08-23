package com.roydon.business.epidemic.enums;

/**
 * @author roydon
 * @date 2023-08-23 11:47【星期三】
 * @description com.roydon.business.epidemic.enums
 * <p> 核酸预约状态 </p>
 **/
public enum natOrderStatusEnum {

    BEGIN("0", "已预约"),
    FINISH("1", "已完成"),
    CANCEL("2", "已取消"),
    ;

    private final String code;
    private final String info;

    natOrderStatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static natOrderStatusEnum findByCode(String code) {
        for (natOrderStatusEnum value : natOrderStatusEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
