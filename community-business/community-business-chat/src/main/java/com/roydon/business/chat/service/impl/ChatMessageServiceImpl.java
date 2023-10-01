package com.roydon.business.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.chat.domain.dto.ChatMessagePageDTO;
import com.roydon.business.chat.domain.entity.ChatMessage;
import com.roydon.business.chat.mapper.ChatMessageMapper;
import com.roydon.business.chat.service.IChatMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 聊天消息Service业务层处理
 *
 * @author roydon
 * @date 2023-08-25
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements IChatMessageService {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    @Override
    public IPage<ChatMessage> selectChatMessageIPage(ChatMessagePageDTO chatMessagePageDTO) {
        LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMessage::getSender, chatMessagePageDTO.getSender()).eq(ChatMessage::getReceiver, chatMessagePageDTO.getReceiver());
        return page(new Page<>(chatMessagePageDTO.getPageNum(), chatMessagePageDTO.getPageSize()), queryWrapper);
    }

    /**
     * 新增聊天消息
     *
     * @param chatMessage 聊天消息
     * @return 结果
     */
    @Override
    public int insertChatMessage(ChatMessage chatMessage) {
        chatMessage.setCreateTime(LocalDateTime.now());
        return chatMessageMapper.insertChatMessage(chatMessage);
    }

    /**
     * 删除聊天消息信息
     *
     * @param messageId 聊天消息主键
     * @return 结果
     */
    @Override
    public boolean deleteChatMessageByMessageId(Long messageId) {
        return removeById(messageId);
    }
}
