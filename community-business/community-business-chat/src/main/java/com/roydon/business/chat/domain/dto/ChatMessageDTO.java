package com.roydon.business.chat.domain.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {

    private String sender;
    private String senderAvatar;
    private String receiver;
    private String receiverAvatar;
    private String content;
    private boolean sendType; // true-发送，false-接收
    private String type; // 0文本1图片
    private Long time;

}
