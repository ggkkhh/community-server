package com.roydon.business.mall.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (MallUserAddress)实体类
 *
 * @author roydon
 * @since 2023-05-18 23:14:25
 */
@Data
@TableName("mall_user_address")
public class MallUserAddress implements Serializable {
    private static final long serialVersionUID = 187079772473647163L;

    @TableId("address_id")
    private String addressId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 省代码
     */
    private String provinceCode;
    /**
     * 市代码
     */
    private String cityCode;
    /**
     * 区县代码
     */
    private String regionCode;
    /**
     * 社区id
     */
    private String communityId;
    /**
     * 备注
     */
    private String nickname;
    /**
     * 号码
     */
    private String telephone;
    /**
     * 详细地址
     */
    private String completeAddress;
    /**
     * 状态：0；1默认收货地址；
     */
    private String active;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private String remark;


}

