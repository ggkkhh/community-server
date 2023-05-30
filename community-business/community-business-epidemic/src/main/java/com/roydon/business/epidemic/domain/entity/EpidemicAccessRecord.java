package com.roydon.business.epidemic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 出入社区人员记录对象 epidemic_access_record
 *
 * @author roydon
 * @date 2023-05-26
 */
@Data
@TableName("epidemic_access_record")
public class EpidemicAccessRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId("record_id")
    private String recordId;

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
     * 进出类型0进入1出
     */
    @Excel(name = "进出类型0进入1出")
    private String accessType;

    /**
     * 出：目的地；进：来源地
     */
    @Excel(name = "出：目的地；进：来源地")
    private String destination;

    /**
     * 0正常2已删除
     */
    private String delFlag;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String remark;

}
