package com.roydon.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.app.domain.entity.AppHotline;

import java.util.List;

/**
 * 紧急热线Service接口
 *
 * @author roydon
 * @date 2023-08-07
 */
public interface IAppHotlineService extends IService<AppHotline> {
    /**
     * 查询紧急热线
     *
     * @param hotlineId 紧急热线主键
     * @return 紧急热线
     */
    AppHotline selectAppHotlineByHotlineId(Long hotlineId);

    /**
     * 查询紧急热线列表
     *
     * @param appHotline 紧急热线
     * @return 紧急热线集合
     */
    List<AppHotline> selectAppHotlineList(AppHotline appHotline);

    /**
     * app端：查询紧急热线列表
     *
     * @param appHotline 紧急热线
     * @return 紧急热线集合
     */
    List<AppHotline> selectAppHotlineListForApp(AppHotline appHotline);

    /**
     * 新增紧急热线
     *
     * @param appHotline 紧急热线
     * @return 结果
     */
    int insertAppHotline(AppHotline appHotline);

    /**
     * 修改紧急热线
     *
     * @param appHotline 紧急热线
     * @return 结果
     */
    int updateAppHotline(AppHotline appHotline);

    /**
     * 改变状态
     *
     * @param appHotline 紧急热线
     * @return boolean
     */
    boolean changeStatus(AppHotline appHotline);

    /**
     * 批量删除紧急热线
     *
     * @param hotlineIds 需要删除的紧急热线主键集合
     * @return 结果
     */
    int deleteAppHotlineByHotlineIds(Long[] hotlineIds);

    /**
     * 删除紧急热线信息
     *
     * @param hotlineId 紧急热线主键
     * @return 结果
     */
    int deleteAppHotlineByHotlineId(Long hotlineId);
}
