package com.roydon.business.mall.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (MallOrder)实体类
 *
 * @author roydon
 * @since 2023-05-18 23:14:11
 */
@Data
@TableName("mall_order")
public class MallOrder implements Serializable {
    private static final long serialVersionUID = -21599664428036113L;

    @TableId("order_id")
    private String orderId;

    private String userId;
    private String userName;
    /**
     * 收货地址id
     */
    private String addressId;
    /**
     * 实际付款总价
     */
    private Double totalPrice;
    /**
     * 0未付款1已付款
     */
    private String payStatus;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private String remark;

}

