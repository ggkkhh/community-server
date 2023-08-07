package com.roydon.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.app.domain.entity.AppHotline;

import java.util.List;

/**
 * 紧急热线Mapper接口
 *
 * @author roydon
 * @date 2023-08-07
 */
public interface AppHotlineMapper extends BaseMapper<AppHotline> {

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
     * 删除紧急热线
     *
     * @param hotlineId 紧急热线主键
     * @return 结果
     */
    int deleteAppHotlineByHotlineId(Long hotlineId);

    /**
     * 批量删除紧急热线
     *
     * @param hotlineIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAppHotlineByHotlineIds(Long[] hotlineIds);
}
