package com.roydon.business.epidemic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;

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
     * 真实姓名
     */
    @Excel(name = "联系电话")
    private String telephone;

    /**
     * 进出类型0进入1出
     */
    @Excel(name = "进出类型0出1进")
    private String accessType;

    /**
     * 报备类型：0手动1管理员录入
     */
    @Excel(name = "报备类型：0手动1管理员录入")
    private String reportType;

    /**
     * 出：目的地；进：来源地
     */
    @Excel(name = "出发地")
    private String placeStart;

    /**
     * 出：目的地；进：来源地
     */
    @Excel(name = "目的地")
    private String placeEnd;

    /**
     * 0正常2已删除
     */
    private String delFlag;

}
