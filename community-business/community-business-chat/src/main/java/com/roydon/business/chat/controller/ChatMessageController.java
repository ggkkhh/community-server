package com.roydon.business.chat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.chat.domain.dto.ChatMessagePageDTO;
import com.roydon.business.chat.domain.entity.ChatMessage;
import com.roydon.business.chat.service.IChatMessageService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 聊天消息Controller
 *
 * @author roydon
 * @date 2023-08-25
 */
@RestController
@RequestMapping("/chat/message")
public class ChatMessageController {

    @Resource
    private IChatMessageService chatMessageService;

    /**
     * 查询聊天消息列表
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody ChatMessagePageDTO chatMessagePageDTO) {
        IPage<ChatMessage> iPage = chatMessageService.selectChatMessageIPage(chatMessagePageDTO);
        return AjaxResult.genTableData(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * 新增聊天消息
     */
    @PostMapping
    public AjaxResult add(@RequestBody ChatMessage chatMessage) {
        return AjaxResult.success(chatMessageService.insertChatMessage(chatMessage));
    }

    /**
     * 删除聊天消息
     */
    @DeleteMapping("/{messageId}")
    public AjaxResult remove(@PathVariable Long messageId) {
        return AjaxResult.success(chatMessageService.deleteChatMessageByMessageId(messageId));
    }

}
