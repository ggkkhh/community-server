package com.roydon.unit.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 部门表(SysUnit)实体类
 *
 * @author roydon
 * @since 2023-05-22 03:53:02
 */
@Data
@TableName("sys_unit")
public class SysUnit implements Serializable {
    private static final long serialVersionUID = 770215529841466989L;
    /**
     * id
     */
    @TableId("unit_id")
    private Long unitId;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 祖级列表
     */
    private String ancestors;
    /**
     * 名称
     */
    private String unitName;
    /**
     * 简称
     */
    private String remark;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 负责人
     */
    private String leader;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;


}

