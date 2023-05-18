package com.roydon.business.news.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (MallOrder)实体类
 *
 * @author roydon
 * @since 2023-05-18 23:06:30
 */
@Data
@TableName("mall_order")
public class MallOrder implements Serializable {
    private static final long serialVersionUID = -65432769113021702L;

    @TableId("order_id")
    private String orderId;

    private String userId;
    /**
     * 收货地址id
     */
    private String addressId;
    /**
     * 关联订单商品
     */
    private String orderGoodsId;
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

