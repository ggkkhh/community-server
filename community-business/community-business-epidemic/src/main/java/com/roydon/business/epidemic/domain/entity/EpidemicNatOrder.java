package com.roydon.business.epidemic.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 预约核酸检测NAT对象 epidemic_nat_order
 *
 * @author roydon
 * @date 2023-08-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("epidemic_nat_order")
public class EpidemicNatOrder extends BaseEntity {
    private static final long serialVersionUID=1L;

    /** 预约id */
    @TableId(value = "order_id",type = IdType.AUTO)
    private Long orderId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户账号 */
    @Excel(name = "用户账号")
    private String username;

    /** 姓名 */
    @Excel(name = "姓名")
    private String realName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String telephone;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCard;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderTime;

    /** 预约状态(0已预约，1已完成，2已取消) */
    @Excel(name = "预约状态(0已预约，1已完成，2已取消)")
    private String orderStatus;


}
