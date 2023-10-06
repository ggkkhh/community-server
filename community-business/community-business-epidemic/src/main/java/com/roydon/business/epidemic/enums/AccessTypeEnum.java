package com.roydon.business.epidemic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author roydon
 * @date 2023-08-21 21:03【星期一】
 * @description com.roydon.business.epidemic.enums
 * <p> 进出社区类型 </p>
 **/
@Getter
@AllArgsConstructor
public enum AccessTypeEnum {

    OUT("0", "出"), IN("1", "进");

    private final String code;
    private final String info;

    public static AccessTypeEnum findByCode(String code) {
        for (AccessTypeEnum value : AccessTypeEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
