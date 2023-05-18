package com.roydon.business.mall.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (MallUserCart)实体类
 *
 * @author roydon
 * @since 2023-05-18 23:14:32
 */
@Data
@TableName("mall_user_cart")
public class MallUserCart implements Serializable {
    private static final long serialVersionUID = -71659594271224095L;

    @TableId("cart_id")
    private String cartId;

    private String userId;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 选择状态0未选1已选
     */
    private String active;


}

