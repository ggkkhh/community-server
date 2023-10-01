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
 * 隔离记录对象 epidemic_isolation_record
 *
 * @author roydon
 * @date 2023-08-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("epidemic_isolation_record")
public class EpidemicIsolationRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "record_id",type = IdType.AUTO)
    private Long recordId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long userId;

    /**
     * 用户
     */
    @Excel(name = "用户")
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
     * 隔离天数（单位天）
     */
    @Excel(name = "隔离天数")
    private Integer isolationTime;

    /**
     * 剩余隔离天数
     */
    @Excel(name = "剩余隔离天数")
    private Integer remainingIsolationTime;

    /**
     * 隔离结束时间
     */
    @Excel(name = "隔离结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date isolationFinishTime;

    /**
     * 0正常2已删除
     */
    private String delFlag;


}
