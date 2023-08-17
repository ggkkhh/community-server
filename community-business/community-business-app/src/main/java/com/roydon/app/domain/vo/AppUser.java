package com.roydon.app.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.roydon.common.core.domain.BaseEntity;
import com.roydon.common.core.domain.entity.SysDept;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * project : community-server
 * <p> 返回给app端的用户信息 </p>
 *
 * @author roydon
 * @date 2023-07-18【星期二】
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AppUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 单元ID
     */
    @ApiModelProperty("单元ID")
    private Long deptId;

    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phonenumber;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idCard;

    /**
     * 用户性别
     */
    @ApiModelProperty("用户性别")
    private String sex;

    @ApiModelProperty("年龄")
    private Integer age;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("是否为租客（0代表房东 1代表租客）")
    private String isTenant;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @ApiModelProperty("最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    /**
     * 单元对象
     */
    @ApiModelProperty("单元对象")
    private SysDept dept;

}

