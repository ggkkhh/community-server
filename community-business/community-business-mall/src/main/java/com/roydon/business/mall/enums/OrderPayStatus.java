package com.roydon.business.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OrderPayStatus
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/11
 * 订单支付状态:0未付款1已付款
 **/
@Getter
@AllArgsConstructor
public enum OrderPayStatus {

    NO_PAY("0", "未付款"),

    PAYED("1", "已付款"),
    ;

    private final String code;
    private final String info;

    public static OrderPayStatus findByCode(String code) {
        for (OrderPayStatus value : OrderPayStatus.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
