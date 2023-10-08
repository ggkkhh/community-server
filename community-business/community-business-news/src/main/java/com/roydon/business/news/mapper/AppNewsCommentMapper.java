package com.roydon.business.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.news.domain.entity.AppNewsComment;

/**
 * (AppNewsComment)表数据库访问层
 *
 * @author roydon
 * @since 2023-10-08 21:07:05
 */
public interface AppNewsCommentMapper extends BaseMapper<AppNewsComment>{

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 影响行数
     */
    int deleteById(Long commentId);

}

