package com.roydon.business.epidemic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author roydon
 * @date 2023-08-08 22:33【星期二】
 * @description com.roydon.business.epidemic.enums
 * <p> 健康码状态 </p>
 **/
@Getter
@AllArgsConstructor
public enum CodeStatusEnum {

    GREEN("0", "绿"), YELLOW("1", "黄"), RED("2", "红");

    private final String code;
    private final String info;

    public static CodeStatusEnum findByCode(String code) {
        for (CodeStatusEnum value : CodeStatusEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
