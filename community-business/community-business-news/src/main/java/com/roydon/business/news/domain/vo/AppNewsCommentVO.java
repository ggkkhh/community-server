package com.roydon.business.news.domain.vo;

import com.roydon.business.news.domain.entity.AppNewsComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * AppNewsCommentVO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/8
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AppNewsCommentVO extends AppNewsComment {
    // 评论者昵称
    private String nickName;
    // 评论者头像
    private String avatar;

    // 被回复者id
    private Long replayUserId;
    // 被回复者昵称
    private String replayUserNickName;

    // 子评论，默认二级
    private List<AppNewsCommentVO> children;
}
