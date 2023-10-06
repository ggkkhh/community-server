package com.roydon.business.epidemic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author roydon
 * @date 2023-08-10 20:52【星期四】
 * @description com.roydon.business.epidemic.enums
 * <p> 审核状态 </p>
 * 0待审核1审核通过2审核不通过
 **/
@Getter
@AllArgsConstructor
public enum AuditStatusEnum {

    WAITING("0", "待审核"), PASS("1", "审核通过"), NOT_PASS("2", "审核不通过");

    private final String code;
    private final String info;

    public static AuditStatusEnum findByCode(String code) {
        for (AuditStatusEnum value : AuditStatusEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
