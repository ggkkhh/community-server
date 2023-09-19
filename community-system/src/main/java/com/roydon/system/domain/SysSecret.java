package com.roydon.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 密钥对象 sys_secret
 *
 * @author roydon
 * @date 2023-08-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_secret")
public class SysSecret extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 密钥主键
     */
    @TableId(value = "secret_id",type = IdType.AUTO)
    private Long secretId;

    /**
     * 密钥名称
     */
    @Excel(name = "密钥名称")
    private String secretName;

    /**
     * 密钥关键字
     */
    @Excel(name = "密钥关键字")
    private String secretKey;

    /**
     * 密钥键名
     */
    @Excel(name = "密钥键名")
    private String keyId;

    /**
     * 密钥键值
     */
    @Excel(name = "密钥键值")
    private String keySecret;

}
