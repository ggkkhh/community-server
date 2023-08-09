package com.roydon.business.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.news.domain.AppNews;
import com.roydon.business.news.domain.vo.HotNews;
import com.roydon.business.news.mapper.AppNewsMapper;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.core.redis.RedisCache;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.StringUtils;
import com.roydon.common.utils.bean.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author roydon
 * @description 针对表【app_news】的数据库操作Service实现
 * @createDate 2023-05-13 13:51:44
 */
@Slf4j
@Service
public class AppNewsServiceImpl extends ServiceImpl<AppNewsMapper, AppNews> implements AppNewsService {

    @Resource
    private RedisCache redisCache;

    @Resource
    private AppNewsMapper appNewsMapper;

    @Async
    @PostConstruct
    public void init() {
        // TODO 如果爬取新闻事件被触发，也要调用此方法重新将新数据写入缓存
        // TODO 优化根据索引--两个字段
        log.info("新闻浏览量写入缓存开始==>");
        LambdaQueryWrapper<AppNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(AppNews::getNewsId, AppNews::getViewNum);
        List<AppNews> appNewsList = list(queryWrapper);
        Map<String, Integer> newsViewMap = appNewsList.stream().collect(Collectors.toMap(AppNews::getNewsId, AppNews::getViewNum));
        redisCache.setCacheMap(CacheConstants.NEWS_VIEW_NUM_KEY, newsViewMap);
        log.info("<==新闻浏览量写入缓存成功");
    }

    //=============================私有方法区=================
    private void getNewsViewNumFromRedis(AppNews appNews) {
        appNews.setViewNum(redisCache.getCacheMapValue(CacheConstants.NEWS_VIEW_NUM_KEY, appNews.getNewsId()));
    }

    private void getHotNewsViewNumFromRedis(HotNews hotNews) {
        hotNews.setViewNum(redisCache.getCacheMapValue(CacheConstants.NEWS_VIEW_NUM_KEY, hotNews.getNewsId()));
    }

    //=======================================================

    @Override
    public List<AppNews> getNewsList(AppNews appNews) {
        List<AppNews> appNewsList = appNewsMapper.selectAppNewsList(appNews);
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
    public boolean changeNewsShowType(AppNews appNews) {
        LambdaUpdateWrapper<AppNews> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AppNews::getNewsId, appNews.getNewsId()).set(AppNews::getShowType, appNews.getShowType());
        return update(updateWrapper);
    }

    @Override
    public boolean removeNewsByIds(String[] newsIds) {
        boolean b = removeBatchByIds(Arrays.asList(newsIds));
        if (b) {
            //删除redis缓存
            for (String newsId : newsIds) {
                redisCache.deleteCacheMapValue(CacheConstants.NEWS_VIEW_NUM_KEY, newsId);
            }
        }
        return b;
    }

    @Override
    public boolean editNews(AppNews appNews) {
        return updateById(appNews);
    }

    /**
     * 新闻浏览量加一业务
     *
     * @param newsId 新闻id
     */
    @Override
    public void viewNumIncrease(String newsId) {
        // 缓存中阅读量自增一
        redisCache.incrementCacheMapValue(CacheConstants.NEWS_VIEW_NUM_KEY, newsId, 1);
    }

    /**
     * 最近两天热点新闻
     *
     * @return List<AppNews>
     */
    @Override
    public List<HotNews> getHotNews() {
        // 若缓存不为空，直接查缓存
        List<HotNews> cacheHotNewsList = redisCache.getCacheList(CacheConstants.NEWS_HOT_NEWS);
        if (StringUtils.isNotEmpty(cacheHotNewsList)) {
            // 从redis读取新闻浏览量
            cacheHotNewsList.forEach(this::getHotNewsViewNumFromRedis);
            return cacheHotNewsList;
        } else {
            // 缓存为空，写入缓存再返回数据
            return setHotNewsToCache();
        }
    }

    /**
     * 最近两天热点新闻10条写入缓存
     *
     * @return ids
     */
    @Override
    public List<HotNews> setHotNewsToCache() {
        log.info("热点新闻写入缓存==>");
        if (StringUtils.isNotEmpty(redisCache.getCacheList(CacheConstants.NEWS_HOT_NEWS))) {
            redisCache.deleteObject(CacheConstants.NEWS_HOT_NEWS);
        }
        // 筛选最近两天的数据
        LambdaQueryWrapper<AppNews> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(AppNews::getNewsId);
        queryWrapper.between(AppNews::getPostTime, DateUtils.getTimeBeforeDay(2L), LocalDateTime.now());
        // 按照浏览量降序
        queryWrapper.orderByDesc(AppNews::getViewNum);
        // limit十条
        IPage<AppNews> newsPage = page(new Page<>(0, 10), queryWrapper);
        List<AppNews> newsList = newsPage.getRecords();
        // AppNews转为HotNews存入redis
        List<HotNews> hotNewsList = BeanCopyUtils.copyBeanList(newsList, HotNews.class);
        redisCache.setCacheList(CacheConstants.NEWS_HOT_NEWS, hotNewsList);
        List<String> hotNewsIds = newsList.stream().map(AppNews::getNewsId).collect(Collectors.toList());
        log.info("==>hotNewsIds：{}", hotNewsIds);
        log.info("<==热点新闻写入缓存结束");
        return hotNewsList;
    }
}




