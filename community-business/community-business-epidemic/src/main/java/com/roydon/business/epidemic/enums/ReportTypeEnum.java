package com.roydon.business.epidemic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出入社区报备的类型
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/3
 **/
@Getter
@AllArgsConstructor
public enum ReportTypeEnum {

    USER("0", "手动报备"),
    ADMIN("1", "管理录入");

    private final String code;
    private final String info;

    public static ReportTypeEnum findByCode(String code) {
        for (ReportTypeEnum value : ReportTypeEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
