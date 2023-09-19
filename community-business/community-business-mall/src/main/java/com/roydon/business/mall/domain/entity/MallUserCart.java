package com.roydon.business.mall.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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

    @TableId(value = "cart_id",type = IdType.ASSIGN_ID)
    private String cartId;

    private Long userId;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 数量
     */
    private Integer goodsCount;
    /**
     * 选择状态0未选1已选
     */
    private String defaultActive;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}

