package com.roydon.app.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 紧急热线对象 app_hotline
 *
 * @author roydon
 * @date 2023-08-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("app_hotline")
public class AppHotline extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 热线id
     */
    @TableId(value = "hotline_id",type = IdType.AUTO)
    private Long hotlineId;

    /**
     * 负责人
     */
    @Excel(name = "负责人")
    private String leader;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String telephone;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

}
