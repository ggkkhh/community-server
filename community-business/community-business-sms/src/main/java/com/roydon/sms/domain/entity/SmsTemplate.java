package com.roydon.sms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * (SmsTemplate)实体类
 *
 * @author roydon
 * @since 2023-05-24 19:08:58
 */
@Data
@TableName("sms_template")
public class SmsTemplate extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -57232105199521606L;
    /**
     * 模板id
     */
    private String templateId;
    /**
     * 供应商id
     */
    @Excel(name = "供应商id")
    private String providerId;
    @Excel(name = "供应商名称")
    private String providerName;
    /**
     * 模板名称
     */
    @Excel(name = "模板名称")
    private String templateName;
    /**
     * 模板类型
     */
    @Excel(name = "模板类型")
    private String templateType;
    /**
     * 模板code
     */
    @Excel(name = "模板code")
    private String templateCode;
    /**
     * 模板内容
     */
    @Excel(name = "模板内容")
    private String templateContent;
    /**
     * 状态0正常1禁用
     */
    @Excel(name = "状态0正常1禁用")
    private String status;
    /**
     * 0正常2已删除
     */
    private String delFlag;

}

