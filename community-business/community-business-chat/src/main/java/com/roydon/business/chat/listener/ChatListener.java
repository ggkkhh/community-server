package com.roydon.business.chat.listener;

import com.roydon.business.chat.domain.dto.ChatMessageDTO;
import com.roydon.business.chat.domain.entity.ChatMessage;
import com.roydon.business.chat.event.ChatEvent;
import com.roydon.business.chat.service.IChatMessageService;
import com.roydon.common.utils.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ChatListener {

    @Resource
    private IChatMessageService chatMessageService;

    @EventListener
    @Async
    public void handlerChatEvent(ChatEvent event) {
        ChatMessageDTO chatMessageDTO = event.getChatMessageDTO();
        log.info(chatMessageDTO.getSender() + "在" + chatMessageDTO.getTime() + "发送了消息!");
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(chatMessageDTO.getSender());
        chatMessage.setSenderImage(chatMessageDTO.getSenderAvatar());
        chatMessage.setReceiver(chatMessageDTO.getReceiver());
        chatMessage.setReceiverImage(chatMessageDTO.getReceiverAvatar());
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessage.setCreateTime(DateUtil.long2LocalDateTime(chatMessageDTO.getTime()));
        chatMessageService.insertChatMessage(chatMessage);
    }
}
