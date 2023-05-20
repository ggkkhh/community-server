package com.roydon.business.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.news.domain.AppNews;
import com.roydon.business.news.mapper.AppNewsMapper;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.core.redis.RedisCache;
import com.roydon.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author roydon
 * @description 针对表【app_news】的数据库操作Service实现
 * @createDate 2023-05-13 13:51:44
 */
@Service
public class AppNewsServiceImpl extends ServiceImpl<AppNewsMapper, AppNews> implements AppNewsService {

    private static final Logger log = LoggerFactory.getLogger(AppNewsServiceImpl.class);

    @Resource
    private RedisCache redisCache;

    @PostConstruct
    public void init() {
        log.info("新闻浏览量写入缓存开始==>");
        List<AppNews> appNewsList = list();
        Map<String, Integer> newsViewMap = appNewsList.stream().collect(Collectors.toMap(AppNews::getNewsId, AppNews::getViewNum));
        redisCache.setCacheMap(CacheConstants.NEWS_VIEW_NUM_KEY, newsViewMap);
        log.info("<==新闻浏览量写入缓存成功");
    }

    //=============================公用方法区=================
    private void getNewsViewNumFromRedis(AppNews appNews) {
        appNews.setViewNum(redisCache.getCacheMapValue(CacheConstants.NEWS_VIEW_NUM_KEY, appNews.getNewsId()));
    }

    //=======================================================

    @Override
    public List<AppNews> getNewsList(AppNews appNews) {
        LambdaQueryWrapper<AppNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotEmpty(appNews.getNewsTitle()), AppNews::getNewsTitle, appNews.getNewsTitle())
                .like(StringUtil.isNotEmpty(appNews.getSource()), AppNews::getSource, appNews.getSource())
                .eq(StringUtil.isNotEmpty(appNews.getNewsType()), AppNews::getNewsType, appNews.getNewsType())
                .eq(StringUtil.isNotEmpty(appNews.getShowInApp()), AppNews::getShowInApp, appNews.getShowInApp())
                .between(StringUtil.isNotEmpty(appNews.getParams().get("beginTime")) || StringUtil.isNotEmpty(appNews.getParams().get("endTime")), AppNews::getPostTime, appNews.getParams().get("beginTime"), appNews.getParams().get("endTime"))
                .orderByDesc(AppNews::getPostTime);
        List<AppNews> appNewsList = list(queryWrapper);
        // 从redis读取新闻浏览量
        appNewsList.forEach(this::getNewsViewNumFromRedis);
        return appNewsList;
    }

    @Override
    public AppNews getNewsDetails(String newsId) {
        LambdaQueryWrapper<AppNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtil.isNotEmpty(newsId), AppNews::getNewsId, newsId);
        AppNews one = getOne(queryWrapper);
        this.getNewsViewNumFromRedis(one);
        return one;
    }

    @Override
    public boolean changeNewsStatus(AppNews appNews) {
        LambdaUpdateWrapper<AppNews> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AppNews::getNewsId, appNews.getNewsId()).set(AppNews::getShowInApp, appNews.getShowInApp());
        return update(updateWrapper);
    }

    @Override
    public boolean removeNewsByIds(String[] newsIds) {
        return removeBatchByIds(Arrays.asList(newsIds));
    }

    @Override
    public boolean editNews(AppNews appNews) {
        return updateById(appNews);
    }

    @Override
    public void viewNumIncrease(String newsId) {
        // 缓存中阅读量自增一
        redisCache.incrementCacheMapValue(CacheConstants.NEWS_VIEW_NUM_KEY, newsId, 1);
    }

}




