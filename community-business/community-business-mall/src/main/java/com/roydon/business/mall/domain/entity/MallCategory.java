package com.roydon.business.mall.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品分类对象 mall_category
 *
 * @author roydon
 * @date 2023-08-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("mall_category")
public class MallCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    @TableId(value = "category_id",type = IdType.ASSIGN_ID)
    private Long categoryId;

    /**
     * 父分类id
     */
    @Excel(name = "父分类id")
    private Long parentId;

    /**
     * 祖级列表
     */
    @Excel(name = "祖级列表")
    private String ancestors;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String categoryName;

    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
    private Long orderNum;

    /**
     * 分类状态（0正常 1停用）
     */
    @Excel(name = "分类状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;


}
