package com.roydon.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.app.domain.dto.AppMessagePageDTO;
import com.roydon.app.domain.entity.AppMessage;

import java.util.List;

/**
 * app消息通知Service接口
 *
 * @author roydon
 * @date 2023-08-15
 */
public interface IAppMessageService extends IService<AppMessage> {
    /**
     * 查询app消息通知
     *
     * @param messageId app消息通知主键
     * @return app消息通知
     */
    AppMessage selectAppMessageByMessageId(Long messageId);

    /**
     * 分页查询
     *
     * @param appMessagePageDTO
     * @return
     */
    IPage<AppMessage> queryPage(AppMessagePageDTO appMessagePageDTO);

    /**
     * 查询app消息通知列表
     *
     * @param appMessage app消息通知
     * @return app消息通知集合
     */
    List<AppMessage> selectAppMessageList(AppMessage appMessage);

    /**
     * 新增app消息通知
     *
     * @param appMessage app消息通知
     * @return 结果
     */
    int insertAppMessage(AppMessage appMessage);

    /**
     * 修改app消息通知
     *
     * @param appMessage app消息通知
     * @return 结果
     */
    int updateAppMessage(AppMessage appMessage);

    /**
     * 批量删除app消息通知
     *
     * @param messageIds 需要删除的app消息通知主键集合
     * @return 结果
     */
    int deleteAppMessageByMessageIds(Long[] messageIds);

    /**
     * 删除app消息通知信息
     *
     * @param messageId app消息通知主键
     * @return 结果
     */
    int deleteAppMessageByMessageId(Long messageId);
}
