package com.roydon.business.epidemic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author roydon
 * @date 2023-08-23 11:47【星期三】
 * @description com.roydon.business.epidemic.enums
 * <p> 核酸预约状态 </p>
 **/
@Getter
@AllArgsConstructor
public enum NatOrderStatusEnum {

    BEGIN("0", "已预约"),
    FINISH("1", "已完成"),
    CANCEL("2", "已取消"),
    ;

    private final String code;
    private final String info;

    public static NatOrderStatusEnum findByCode(String code) {
        for (NatOrderStatusEnum value : NatOrderStatusEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
