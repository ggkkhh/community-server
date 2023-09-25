package com.roydon.business.epidemic.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 体温上报对象 epidemic_temperature_report
 *
 * @author roydon
 * @date 2023-09-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("epidemic_temperature_report")
public class EpidemicTemperatureReport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @Excel(name = "用户名")
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
     * 体温
     */
    @Excel(name = "体温（℃）", suffix = "℃")
    private String temperature;

}
