package com.roydon.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.app.domain.entity.AppNotice;
import com.roydon.app.mapper.AppNoticeMapper;
import com.roydon.app.service.IAppNoticeService;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.core.redis.RedisCache;
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

    @Resource
    private RedisCache redisCache;

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
     * app端图文轮播图公告列表
     *
     * @param appNotice app端图文轮播图公告
     * @return app端图文轮播图公告
     */
    @Override
    public List<AppNotice> getAppBanner(AppNotice appNotice) {
        List<AppNotice> noticeList;
        List<AppNotice> cacheList = redisCache.getCacheList(CacheConstants.APP_NOTICE_LIST);
        if (StringUtil.isEmpty(cacheList)) {
            // 缓存为空，就缓存
            LambdaQueryWrapper<AppNotice> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StringUtil.isNotEmpty(appNotice.getShowInApp()), AppNotice::getShowInApp, appNotice.getShowInApp()).orderByDesc(AppNotice::getCreateTime);
            noticeList = list(queryWrapper);
            redisCache.setCacheList(CacheConstants.APP_NOTICE_LIST, noticeList);
        } else {
            return cacheList;
        }
        return noticeList;
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
        redisCache.deleteObject(CacheConstants.APP_NOTICE_LIST);
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
        redisCache.deleteObject(CacheConstants.APP_NOTICE_LIST);
        return appNoticeMapper.updateAppNotice(appNotice);
    }

    @Override
    public boolean changeStatus(AppNotice appNotice) {
        LambdaUpdateWrapper<AppNotice> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AppNotice::getNoticeId, appNotice.getNoticeId()).set(AppNotice::getShowInApp, appNotice.getShowInApp());
        redisCache.deleteObject(CacheConstants.APP_NOTICE_LIST);
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
        redisCache.deleteObject(CacheConstants.APP_NOTICE_LIST);
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
        redisCache.deleteObject(CacheConstants.APP_NOTICE_LIST);
        return appNoticeMapper.deleteAppNoticeByNoticeId(noticeId);
    }

}
