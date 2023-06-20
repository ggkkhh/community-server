package com.roydon.sms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统短信服务供应商(SmsProvider)实体类
 *
 * @author roydon
 * @since 2023-05-24 19:07:29
 */
@ApiModel(value = "SmsProvider", description = "短信服务员提供商")
@Data
@TableName("sms_provider")
public class SmsProvider extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 751957157873429854L;
    /**
     * 短信业务主键
     */
    @TableId("provider_id")
    private String providerId;
    /**
     * 短信服务供应商
     */
    @Excel(name = "短信服务供应商")
    private String providerName;
    /**
     * 服务开始时间
     */
    @Excel(name = "服务开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date provideBeginTime;
    /**
     * 服务结束时间
     */
    @Excel(name = "服务结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date provideEndTime;
    /**
     * 短信剩余发送量
     */
    @Excel(name = "短信剩余发送量")
    private long residueCount;

    @Excel(name = "accessKeyId")
    private String accessKeyId;

    @Excel(name = "accessKeySecret")
    private String accessKeySecret;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;


}

