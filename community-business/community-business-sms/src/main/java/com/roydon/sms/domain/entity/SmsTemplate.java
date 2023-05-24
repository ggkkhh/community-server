package com.roydon.sms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (SmsTemplate)实体类
 *
 * @author roydon
 * @since 2023-05-24 19:08:58
 */
@Data
@TableName("sms_template")
public class SmsTemplate implements Serializable {
    private static final long serialVersionUID = -57232105199521606L;
    /**
     * 模板id
     */
    private String templateId;
    /**
     * 供应商id
     */
    private String providerId;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板类型
     */
    private String templateType;
    /**
     * 模板code
     */
    private String templateCode;
    /**
     * 模板内容
     */
    private String templateContent;
    /**
     * 状态0正常1禁用
     */
    private String status;
    /**
     * 0正常2已删除
     */
    private String delFlag;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private String remark;


}

