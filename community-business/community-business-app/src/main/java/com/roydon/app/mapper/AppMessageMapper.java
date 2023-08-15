package com.roydon.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.app.domain.entity.AppMessage;

import java.util.List;

/**
 * app消息通知Mapper接口
 *
 * @author roydon
 * @date 2023-08-15
 */
public interface AppMessageMapper extends BaseMapper<AppMessage> {

    /**
     * 查询app消息通知
     *
     * @param messageId app消息通知主键
     * @return app消息通知
     */
     AppMessage selectAppMessageByMessageId(Long messageId);

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
     * 删除app消息通知
     *
     * @param messageId app消息通知主键
     * @return 结果
     */
    int deleteAppMessageByMessageId(Long messageId);

    /**
     * 批量删除app消息通知
     *
     * @param messageIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAppMessageByMessageIds(Long[] messageIds);
}
