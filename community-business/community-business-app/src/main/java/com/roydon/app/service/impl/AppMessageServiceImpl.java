package com.roydon.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.app.domain.dto.AppMessagePageDTO;
import com.roydon.app.domain.entity.AppMessage;
import com.roydon.app.mapper.AppMessageMapper;
import com.roydon.app.service.IAppMessageService;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * app消息通知Service业务层处理
 *
 * @author roydon
 * @date 2023-08-15
 */
@Service
public class AppMessageServiceImpl extends ServiceImpl<AppMessageMapper, AppMessage> implements IAppMessageService {

    @Resource
    private AppMessageMapper appMessageMapper;

    /**
     * 查询app消息通知
     *
     * @param messageId app消息通知主键
     * @return app消息通知
     */
    @Override
    public AppMessage selectAppMessageByMessageId(Long messageId) {
        return appMessageMapper.selectAppMessageByMessageId(messageId);
    }

    @Override
    public IPage<AppMessage> queryPage(AppMessagePageDTO appMessagePageDTO) {
        LambdaQueryWrapper<AppMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppMessage::getUserId, SecurityUtils.getUserId());
        queryWrapper.orderByDesc(AppMessage::getUpdateTime, AppMessage::getCreateTime);
        return page(new Page<>(appMessagePageDTO.getPageNum(), appMessagePageDTO.getPageSize()), queryWrapper);
    }

    /**
     * 查询app消息通知列表
     *
     * @param appMessage app消息通知
     * @return app消息通知
     */
    @Override
    public List<AppMessage> selectAppMessageList(AppMessage appMessage) {
        appMessage.setUserId(SecurityUtils.getUserId());
        return appMessageMapper.selectAppMessageList(appMessage);
    }

    /**
     * 新增app消息通知
     *
     * @param appMessage app消息通知
     * @return 结果
     */
    @Override
    public int insertAppMessage(AppMessage appMessage) {
        appMessage.setCreateTime(DateUtils.getNowDate());
        return appMessageMapper.insertAppMessage(appMessage);
    }

    /**
     * 修改app消息通知
     *
     * @param appMessage app消息通知
     * @return 结果
     */
    @Override
    public int updateAppMessage(AppMessage appMessage) {
        appMessage.setUpdateTime(DateUtils.getNowDate());
        appMessage.setUpdateTime(DateUtils.getNowDate());
        return appMessageMapper.updateAppMessage(appMessage);
    }

    /**
     * 批量删除app消息通知
     *
     * @param messageIds 需要删除的app消息通知主键
     * @return 结果
     */
    @Override
    public int deleteAppMessageByMessageIds(Long[] messageIds) {
        return appMessageMapper.deleteAppMessageByMessageIds(messageIds);
    }

    /**
     * 删除app消息通知信息
     *
     * @param messageId app消息通知主键
     * @return 结果
     */
    @Override
    public int deleteAppMessageByMessageId(Long messageId) {
        return appMessageMapper.deleteAppMessageByMessageId(messageId);
    }
}
