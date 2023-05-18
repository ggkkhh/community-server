package com.roydon.business.mall.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (MallOrderGoods)实体类
 *
 * @author roydon
 * @since 2023-05-18 23:13:56
 */
@Data
@TableName("mall_order_goods")
public class MallOrderGoods implements Serializable {
    private static final long serialVersionUID = 789602558626075892L;

    @TableId("id")
    private String id;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 此商品总价格
     */
    private Double price;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 0未收货1已收货
     */
    private String receive;


}

