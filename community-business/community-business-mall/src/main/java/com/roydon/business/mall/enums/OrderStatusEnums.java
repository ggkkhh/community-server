package com.roydon.business.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OrderStatusEnums
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/11
 * 订单状态
 **/
@Getter
@AllArgsConstructor
public enum OrderStatusEnums {
    CREATED("0", "已创建"),

    CANCELED("1", "已取消"),

    DELETED("2", "已删除"),

    COMPLETED("3", "已完成"),
    ;

    private final String code;
    private final String info;

    public static OrderStatusEnums findByCode(String code) {
        for (OrderStatusEnums value : OrderStatusEnums.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
