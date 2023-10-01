package com.roydon.business.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.chat.domain.dto.ChatListPageDTO;
import com.roydon.business.chat.domain.entity.ChatList;
import com.roydon.business.chat.mapper.ChatListMapper;
import com.roydon.business.chat.service.IChatListService;
import com.roydon.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 聊天列Service业务层处理
 *
 * @author roydon
 * @date 2023-08-25
 */
@Service
public class ChatListServiceImpl extends ServiceImpl<ChatListMapper, ChatList> implements IChatListService {

    @Resource
    private ChatListMapper chatListMapper;

    /**
     * 查询聊天列列表
     *
     * @param chatListPageDTO 聊天列
     * @return 聊天列
     */
    @Override
    public IPage<ChatList> selectChatListIPage(ChatListPageDTO chatListPageDTO) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getSender, chatListPageDTO.getSender() == null ? SecurityUtils.getUsername() : chatListPageDTO.getSender());
        return page(new Page<>(chatListPageDTO.getPageNum(), chatListPageDTO.getPageSize()), queryWrapper);
    }

    /**
     * 删除聊天列信息
     *
     * @param listId 聊天列主键
     * @return 结果
     */
    @Override
    public boolean deleteChatListByListId(Long listId) {
        return removeById(listId);
    }
}
