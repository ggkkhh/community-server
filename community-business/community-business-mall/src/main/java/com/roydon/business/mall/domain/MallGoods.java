package com.roydon.business.mall.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (MallGoods)实体类
 *
 * @author roydon
 * @since 2023-05-18 23:14:18
 */
@Data
@TableName("mall_goods")
public class MallGoods implements Serializable {
    private static final long serialVersionUID = -38678073295414031L;
    /**
     * 商品id
     */
    @TableId("goods_id")
    private String goodsId;
    /**
     * 标题
     */
    private String goodsTitle;
    /**
     * 图片
     */
    private String goodsImg;
    /**
     * 商品描述
     */
    private String goodsDetails;
    /**
     * 商品价格
     */
    private Double goodsPrice;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 社区id
     */
    private Long deptId;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 浏览数
     */
    private Integer viewNum;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private String remark;


}

