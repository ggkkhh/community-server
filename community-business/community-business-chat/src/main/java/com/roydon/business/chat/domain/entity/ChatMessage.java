package com.roydon.business.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天消息对象 chat_message
 *
 * @author roydon
 * @date 2023-08-25
 */
@Data
@TableName("chat_message")
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("message_id")
    private Long messageId;

    /**
     * 发送者
     */
    private String sender;
    private String senderImage;

    /**
     * 接收者
     */
    private String receiver;
    private String receiverImage;

    /**
     * 消息内容
     */
    private String content;

    private LocalDateTime createTime;

}
