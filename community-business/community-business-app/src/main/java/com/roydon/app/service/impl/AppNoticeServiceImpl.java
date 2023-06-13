package com.roydon.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.app.domain.entity.AppNotice;
import com.roydon.app.mapper.AppNoticeMapper;
import com.roydon.app.service.IAppNoticeService;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * app端图文轮播图公告(AppNotice)表服务实现类
 *
 * @author roydon
 * @since 2023-06-13 22:10:44
 */
@Service("appNoticeService")
public class AppNoticeServiceImpl extends ServiceImpl<AppNoticeMapper, AppNotice> implements IAppNoticeService {
    @Resource
    private AppNoticeMapper appNoticeMapper;

    /**
     * 查询app端图文轮播图公告
     *
     * @param noticeId app端图文轮播图公告主键
     * @return app端图文轮播图公告
     */
    @Override
    public AppNotice selectAppNoticeByNoticeId(Long noticeId) {
        return appNoticeMapper.selectAppNoticeByNoticeId(noticeId);
    }

    /**
     * 查询app端图文轮播图公告列表
     *
     * @param appNotice app端图文轮播图公告
     * @return app端图文轮播图公告
     */
    @Override
    public List<AppNotice> getAppNoticeList(AppNotice appNotice) {
        LambdaQueryWrapper<AppNotice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotEmpty(appNotice.getNoticeTitle()), AppNotice::getNoticeTitle, appNotice.getNoticeTitle())
                .eq(StringUtil.isNotEmpty(appNotice.getShowInApp()), AppNotice::getShowInApp, appNotice.getShowInApp())
                .between(StringUtil.isNotEmpty(appNotice.getParams().get("beginTime")) || StringUtil.isNotEmpty(appNotice.getParams().get("endTime")), AppNotice::getCreateTime, appNotice.getParams().get("beginTime"), appNotice.getParams().get("endTime"))
                .orderByDesc(AppNotice::getCreateTime);
        return list(queryWrapper);
    }

    /**
     * 新增app端图文轮播图公告
     *
     * @param appNotice app端图文轮播图公告
     * @return 结果
     */
    @Override
    public int insertAppNotice(AppNotice appNotice) {
        appNotice.setCreateTime(DateUtils.getNowDate());
        return appNoticeMapper.insertAppNotice(appNotice);
    }

    /**
     * 修改app端图文轮播图公告
     *
     * @param appNotice app端图文轮播图公告
     * @return 结果
     */
    @Override
    public int updateAppNotice(AppNotice appNotice) {
        appNotice.setUpdateTime(DateUtils.getNowDate());
        return appNoticeMapper.updateAppNotice(appNotice);
    }

    @Override
    public boolean changeStatus(AppNotice appNotice) {
        LambdaUpdateWrapper<AppNotice> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AppNotice::getNoticeId, appNotice.getNoticeId()).set(AppNotice::getShowInApp, appNotice.getShowInApp());
        return update(updateWrapper);
    }

    /**
     * 批量删除app端图文轮播图公告
     *
     * @param noticeIds 需要删除的app端图文轮播图公告主键
     * @return 结果
     */
    @Override
    public int deleteAppNoticeByNoticeIds(Long[] noticeIds) {
        return appNoticeMapper.deleteAppNoticeByNoticeIds(noticeIds);
    }

    /**
     * 删除app端图文轮播图公告信息
     *
     * @param noticeId app端图文轮播图公告主键
     * @return 结果
     */
    @Override
    public int deleteAppNoticeByNoticeId(Long noticeId) {
        return appNoticeMapper.deleteAppNoticeByNoticeId(noticeId);
    }

}
