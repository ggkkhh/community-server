package com.roydon.business.chat.event;

import com.roydon.business.chat.domain.dto.ChatMessageDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * 自定义一个事件
 */
@Getter
public class ChatEvent extends ApplicationEvent {

    ChatMessageDTO chatMessageDTO;

    public ChatEvent(Object source, ChatMessageDTO chatMessageDTO) {
        super(source);
        this.chatMessageDTO = chatMessageDTO;
    }

    public ChatEvent(Object source, Clock clock, ChatMessageDTO chatMessageDTO) {
        super(source, clock);
        this.chatMessageDTO = chatMessageDTO;
    }
}
