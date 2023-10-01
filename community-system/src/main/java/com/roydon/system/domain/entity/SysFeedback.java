package com.roydon.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统反馈对象 sys_feedback
 *
 * @author roydon
 * @date 2023-08-24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_feedback")
public class SysFeedback extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 反馈id
     */
    @TableId(value = "feedback_id",type = IdType.AUTO)
    private Long feedbackId;

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
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String telephone;

    /**
     * 反馈平台（0:app,1:web）
     */
    @Excel(name = "反馈平台", readConverterExp = "0=app,1=web")
    private String feedbackPlatform;

    /**
     * 反馈图片地址
     */
    @Excel(name = "反馈图片地址")
    private String feedbackImage;


}
