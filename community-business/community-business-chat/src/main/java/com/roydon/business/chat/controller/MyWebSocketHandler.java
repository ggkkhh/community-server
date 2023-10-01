package com.roydon.business.chat.controller;

import com.alibaba.fastjson2.JSON;
import com.roydon.business.chat.domain.entity.ChatMessage;
import com.roydon.business.chat.service.IChatMessageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Resource
    private IChatMessageService chatMessageService; // 假设你已经创建了一个MessageRepository用于消息的持久化操作

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // 进行消息持久化操作
        ChatMessage chatMessage = JSON.parseObject(payload, ChatMessage.class);
        chatMessageService.save(chatMessage);

        // 继续处理其他业务逻辑，比如将消息广播给其他在线用户
    }
}
