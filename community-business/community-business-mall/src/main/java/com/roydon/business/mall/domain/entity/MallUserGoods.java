package com.roydon.business.mall.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户具有的商品表(MallUserGoods)实体类
 *
 * @author roydon
 * @since 2023-05-18 23:14:38
 */
@Data
@TableName("mall_user_goods")
public class MallUserGoods implements Serializable {
    private static final long serialVersionUID = -56774690679660783L;

    @TableId("id")
    private String id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品id
     */
    private String goodsId;

    private String delFlag;


}

