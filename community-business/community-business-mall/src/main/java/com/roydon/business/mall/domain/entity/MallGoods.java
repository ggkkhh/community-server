package com.roydon.business.mall.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
     * 是否下架0正常1下架
     */
    private String status;

    /**
     * 浏览数
     */
    private Integer viewNum;
    /**
     * 创建时间
     */
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

