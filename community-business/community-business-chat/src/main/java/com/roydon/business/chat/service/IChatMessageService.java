package com.roydon.business.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.chat.domain.dto.ChatMessagePageDTO;
import com.roydon.business.chat.domain.entity.ChatMessage;

/**
 * 聊天消息Service接口
 *
 * @author roydon
 * @date 2023-08-25
 */
public interface IChatMessageService extends IService<ChatMessage> {

    /**
     * 分页查询
     *
     * @param chatMessagePageDTO 分页参数
     * @return IPage<ChatMessage>
     */
    IPage<ChatMessage> selectChatMessageIPage(ChatMessagePageDTO chatMessagePageDTO);

    /**
     * 新增聊天消息
     *
     * @param chatMessage 聊天消息
     * @return 结果
     */
    int insertChatMessage(ChatMessage chatMessage);

    /**
     * 删除聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 结果
     */
    boolean deleteChatMessageByMessageId(Long messageId);
}
