package com.roydon.business.chat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.chat.domain.dto.ChatListPageDTO;
import com.roydon.business.chat.domain.entity.ChatList;
import com.roydon.business.chat.service.IChatListService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 聊天列Controller
 *
 * @author roydon
 * @date 2023-08-25
 */
@RestController
@RequestMapping("/chat/list")
public class ChatListController {

    @Resource
    private IChatListService chatListService;

    /**
     * 查询聊天列表
     */
    @PostMapping("/normal")
    public AjaxResult normalList(@RequestBody ChatListPageDTO chatListPageDTO) {
        IPage<ChatList> iPage = chatListService.selectChatListIPage(chatListPageDTO);
        return AjaxResult.genTableData(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * 删除聊天列
     */
    @DeleteMapping("/{listId}")
    public AjaxResult remove(@PathVariable Long listId) {
        return AjaxResult.success(chatListService.deleteChatListByListId(listId));
    }

}
