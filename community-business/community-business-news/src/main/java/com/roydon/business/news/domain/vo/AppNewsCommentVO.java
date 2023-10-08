package com.roydon.business.news.domain.vo;

import com.roydon.business.news.domain.entity.AppNewsComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AppNewsCommentVO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/8
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AppNewsCommentVO extends AppNewsComment {
    // 昵称
    private String nickName;
    // 头像
    private String avatar;
}
