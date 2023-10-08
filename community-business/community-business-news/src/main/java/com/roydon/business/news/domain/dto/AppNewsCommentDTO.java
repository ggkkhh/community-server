package com.roydon.business.news.domain.dto;

import com.roydon.business.news.domain.entity.AppNewsComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AppNewsCommentDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/8
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AppNewsCommentDTO extends AppNewsComment {
    private int pageNum;
    private int pageSize;
}
