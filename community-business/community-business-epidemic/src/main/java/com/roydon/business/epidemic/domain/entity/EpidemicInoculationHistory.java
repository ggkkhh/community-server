package com.roydon.business.epidemic.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 疫苗接种记录对象 epidemic_inoculation_history
 *
 * @author roydon
 * @date 2023-08-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("epidemic_inoculation_history")
public class EpidemicInoculationHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    @TableId(value = "history_id",type = IdType.AUTO)
    private Long historyId;

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


}
