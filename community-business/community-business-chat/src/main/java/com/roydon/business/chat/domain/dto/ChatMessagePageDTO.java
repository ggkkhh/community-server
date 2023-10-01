package com.roydon.business.chat.domain.dto;

import com.roydon.business.chat.domain.entity.ChatMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author roydon
 * @date 2023-08-25 22:59【星期五】
 * @description com.roydon.business.chat.domain.dto
 * <p> 查询参数 </p>
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMessagePageDTO extends ChatMessage {
    private Integer pageNum;
    private Integer pageSize;
}
