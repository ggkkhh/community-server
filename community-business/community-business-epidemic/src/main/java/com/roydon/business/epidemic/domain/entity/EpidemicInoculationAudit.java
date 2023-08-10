package com.roydon.business.epidemic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 疫苗接种审核对象 epidemic_inoculation_audit
 *
 * @author roydon
 * @date 2023-08-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("epidemic_inoculation_audit")
public class EpidemicInoculationAudit extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long auditId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long userId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String username;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名")
    private String realName;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String telephone;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idCard;

    /**
     * 待审核图片
     */
    @Excel(name = "待审核图片")
    private String auditImage;

    /**
     * 审核状态(0待审核1审核通过2审核不通过)
     */
    @Excel(name = "审核状态(0待审核1审核通过2审核不通过)")
    private String auditStatus;


}
