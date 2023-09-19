package com.roydon.business.mall.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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

    @TableId(value = "order_id", type = IdType.ASSIGN_ID)
    private String orderId;

    private Long userId;
    /**
     * 下单账号
     */
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
    /**
     * 支付单号
     */
    private String payOrderId;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    private String remark;

}

