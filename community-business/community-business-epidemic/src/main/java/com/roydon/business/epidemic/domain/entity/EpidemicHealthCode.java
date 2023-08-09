package com.roydon.business.epidemic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 居民健康码对象 epidemic_health_code
 *
 * @author roydon
 * @date 2023-08-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("epidemic_health_code")
public class EpidemicHealthCode extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 健康码id
     */
    @TableId("code_id")
    private Long codeId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long userId;

    /**
     * 账号
     */
    @Excel(name = "账号")
    private String userName;

    /**
     * 健康码base64格式
     */
    @Excel(name = "健康码base64格式")
    private String codeBase64;

    /**
     * 健康码图片格式
     */
    @Excel(name = "健康码图片格式")
    private String codeImage;

    /**
     * 健康码状态（0绿；1黄；2红）
     */
    @Excel(name = "健康码状态", readConverterExp = "0绿；1黄；2红")
    private String codeStatus;


}
