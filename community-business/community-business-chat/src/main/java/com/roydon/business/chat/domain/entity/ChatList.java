package com.roydon.business.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天列对象 chat_list
 *
 * @author roydon
 * @date 2023-08-25
 */
@Data
@TableName("chat_list")
public class ChatList implements Serializable {
    private static final long serialVersionUID=1L;

    /** id */
    @TableId("list_id")
    private Long listId;

    /** 发送者 */
    private String sender;
    private String senderImage;

    /** 接收者 */
    private String receiver;
    private String receiverImage;

    /** 新消息内容 */
    private String newContent;

    /** 聊天类型,0:私聊 */
    private String listType;

    /** 状态，0:正常,1已读,2:删除,9:置顶 */
    private String status;

    /** 更新时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
