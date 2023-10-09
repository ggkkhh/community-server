package com.roydon.business.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.news.domain.dto.AppNewsCommentDTO;
import com.roydon.business.news.domain.entity.AppNewsComment;
import com.roydon.business.news.mapper.AppNewsCommentMapper;
import com.roydon.business.news.service.IAppNewsCommentService;
import com.roydon.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * (AppNewsComment)表服务实现类
 *
 * @author roydon
 * @since 2023-10-08 21:07:06
 */
@Service("appNewsCommentService")
public class AppNewsCommentServiceImpl extends ServiceImpl<AppNewsCommentMapper, AppNewsComment> implements IAppNewsCommentService {

    /**
     * 分页查询
     *
     * @param pageDTO
     */
    @Override
    public IPage<AppNewsComment> getTreeByNewsId(AppNewsCommentDTO pageDTO) {
        LambdaQueryWrapper<AppNewsComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppNewsComment::getNewsId, pageDTO.getNewsId());
        return this.page(new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize()), queryWrapper);
    }

    @Override
    public IPage<AppNewsComment> getRootListByNewsId(AppNewsCommentDTO pageDTO) {
        LambdaQueryWrapper<AppNewsComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppNewsComment::getNewsId, pageDTO.getNewsId());
        queryWrapper.eq(AppNewsComment::getParentId, 0);
        return this.page(new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize()), queryWrapper);
    }

    @Override
    public List<AppNewsComment> getChildren(Long commentId) {
        LambdaQueryWrapper<AppNewsComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppNewsComment::getOriginId, commentId);
        return list(queryWrapper);
    }

    /**
     * 新增数据
     *
     * @param appNewsComment 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(AppNewsComment appNewsComment) {
        appNewsComment.setCreateTime(LocalDateTime.now());
        appNewsComment.setUserId(SecurityUtils.getUserId());
        // 默认顶级评论
        appNewsComment.setParentId(0L);
        appNewsComment.setOriginId(0L);
        return save(appNewsComment);
    }

    @Override
    public boolean replay(AppNewsComment appNewsComment) {
        appNewsComment.setCreateTime(LocalDateTime.now());
        // 前端需要携带parentId
        appNewsComment.setParentId(appNewsComment.getParentId());
        appNewsComment.setOriginId(appNewsComment.getOriginId());
        appNewsComment.setUserId(SecurityUtils.getUserId());
        return save(appNewsComment);
    }

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long commentId) {
        return removeById(commentId);
    }
}
