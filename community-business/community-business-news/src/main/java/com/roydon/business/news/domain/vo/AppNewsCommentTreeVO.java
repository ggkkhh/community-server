package com.roydon.business.news.domain.vo;

import com.roydon.business.news.domain.entity.AppNewsComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * NewsCommentTreeVo
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/8
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AppNewsCommentTreeVO extends AppNewsCommentVO {

    // 子评论，默认二级
    private List<AppNewsComment> children;
}
