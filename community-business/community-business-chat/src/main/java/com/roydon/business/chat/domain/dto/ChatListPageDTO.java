package com.roydon.business.chat.domain.dto;

import com.roydon.business.chat.domain.entity.ChatList;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author roydon
 * @date 2023-08-25 23:17【星期五】
 * @description com.roydon.business.chat.domain.dto
 * <p> 分页dto </p>
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatListPageDTO extends ChatList {
    private Integer pageNum;
    private Integer pageSize;
}
