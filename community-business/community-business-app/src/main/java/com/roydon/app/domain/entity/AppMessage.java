package com.roydon.app.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.annotation.Excel;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * app消息通知对象 app_message
 *
 * @author roydon
 * @date 2023-08-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("app_message")
public class AppMessage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("message_id")
    private Long messageId;

    /**
     * 通知用户id
     */
    @Excel(name = "通知用户id")
    private Long userId;

    /**
     * 通知内容
     */
    @Excel(name = "通知内容")
    private String messageContent;

    /**
     * 消息状态(0未读1已读)
     */
    @Excel(name = "消息状态(0未读1已读)")
    private String messageStatus;


}
