package com.roydon.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.app.domain.entity.AppHotline;
import com.roydon.app.mapper.AppHotlineMapper;
import com.roydon.app.service.IAppHotlineService;
import com.roydon.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 紧急热线Service业务层处理
 *
 * @author roydon
 * @date 2023-08-07
 */
@Service
public class AppHotlineServiceImpl extends ServiceImpl<AppHotlineMapper, AppHotline> implements IAppHotlineService {

    @Resource
    private AppHotlineMapper appHotlineMapper;

    /**
     * 查询紧急热线
     *
     * @param hotlineId 紧急热线主键
     * @return 紧急热线
     */
    @Override
    public AppHotline selectAppHotlineByHotlineId(Long hotlineId) {
        return appHotlineMapper.selectAppHotlineByHotlineId(hotlineId);
    }

    /**
     * 查询紧急热线列表
     *
     * @param appHotline 紧急热线
     * @return 紧急热线
     */
    @Override
    public List<AppHotline> selectAppHotlineList(AppHotline appHotline) {
        return appHotlineMapper.selectAppHotlineList(appHotline);
    }

    /**
     * app端：查询紧急热线列表
     *
     * @param appHotline 紧急热线
     * @return 紧急热线集合
     */
    @Override
    public List<AppHotline> selectAppHotlineListForApp(AppHotline appHotline) {
        // 筛选状态为可用
        LambdaQueryWrapper<AppHotline> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppHotline::getStatus, appHotline.getStatus()).orderByDesc(AppHotline::getUpdateTime);
        return list(queryWrapper);
    }

    /**
     * 新增紧急热线
     *
     * @param appHotline 紧急热线
     * @return 结果
     */
    @Override
    public int insertAppHotline(AppHotline appHotline) {
        appHotline.setCreateTime(DateUtils.getNowDate());
        return appHotlineMapper.insertAppHotline(appHotline);
    }

    /**
     * 修改紧急热线
     *
     * @param appHotline 紧急热线
     * @return 结果
     */
    @Override
    public int updateAppHotline(AppHotline appHotline) {
        appHotline.setUpdateTime(DateUtils.getNowDate());
        return appHotlineMapper.updateAppHotline(appHotline);
    }

    @Override
    public boolean changeStatus(AppHotline appHotline) {
        LambdaUpdateWrapper<AppHotline> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AppHotline::getHotlineId, appHotline.getHotlineId()).set(AppHotline::getStatus, appHotline.getStatus());
        return update(updateWrapper);
    }

    /**
     * 批量删除紧急热线
     *
     * @param hotlineIds 需要删除的紧急热线主键
     * @return 结果
     */
    @Override
    public int deleteAppHotlineByHotlineIds(Long[] hotlineIds) {
        return appHotlineMapper.deleteAppHotlineByHotlineIds(hotlineIds);
    }

    /**
     * 删除紧急热线信息
     *
     * @param hotlineId 紧急热线主键
     * @return 结果
     */
    @Override
    public int deleteAppHotlineByHotlineId(Long hotlineId) {
        return appHotlineMapper.deleteAppHotlineByHotlineId(hotlineId);
    }
}
