package com.roydon.business.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.chat.domain.entity.ChatMessage;

/**
 * 聊天消息Mapper接口
 *
 * @author roydon
 * @date 2023-08-25
 */
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    /**
     * 新增聊天消息
     *
     * @param chatMessage 聊天消息
     * @return 结果
     */
    int insertChatMessage(ChatMessage chatMessage);

    /**
     * 删除聊天消息
     *
     * @param messageId 聊天消息主键
     * @return 结果
     */
    int deleteChatMessageByMessageId(Long messageId);

}
