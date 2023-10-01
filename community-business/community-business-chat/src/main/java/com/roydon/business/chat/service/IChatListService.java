package com.roydon.business.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.chat.domain.dto.ChatListPageDTO;
import com.roydon.business.chat.domain.entity.ChatList;

/**
 * 聊天列Service接口
 *
 * @author roydon
 * @date 2023-08-25
 */
public interface IChatListService extends IService<ChatList> {

    /**
     * 查询聊天列列表
     *
     * @param chatListPageDTO 聊天列
     * @return 聊天列集合
     */
    IPage<ChatList> selectChatListIPage(ChatListPageDTO chatListPageDTO);

    /**
     * 删除聊天列信息
     *
     * @param listId 聊天列主键
     * @return boolean
     */
    boolean deleteChatListByListId(Long listId);
}
