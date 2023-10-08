package com.roydon.business.news.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.news.domain.dto.AppNewsCommentDTO;
import com.roydon.business.news.domain.entity.AppNewsComment;

/**
 * (AppNewsComment)表服务接口
 *
 * @author roydon
 * @since 2023-10-08 21:07:06
 */
public interface IAppNewsCommentService extends IService<AppNewsComment> {

    /**
     * 分页查询
     */
    IPage<AppNewsComment> getTreeByNewsId(AppNewsCommentDTO pageDTO);

    /**
     * 新增数据
     *
     * @param appNewsComment 实例对象
     * @return 实例对象
     */
    boolean insert(AppNewsComment appNewsComment);

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 是否成功
     */
    boolean deleteById(Long commentId);

    boolean apply(AppNewsComment appNewsComment);
}
