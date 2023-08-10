package com.roydon.business.epidemic.enums;

/**
 * @author roydon
 * @date 2023-08-10 20:52【星期四】
 * @description com.roydon.business.epidemic.enums
 * <p> 审核状态 </p>
 * 0待审核1审核通过2审核不通过
 **/
public enum AuditStatusEnum {

    WAITING("0", "待审核"), PASS("1", "审核通过"), NOTPASS("2", "审核不通过");

    private final String code;
    private final String info;

    AuditStatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static AuditStatusEnum findByCode(String code) {
        for (AuditStatusEnum value : AuditStatusEnum.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
