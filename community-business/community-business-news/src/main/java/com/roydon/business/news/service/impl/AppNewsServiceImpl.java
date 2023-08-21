package com.roydon.business.news.service.impl;

import cn.hutool.core.util.BooleanUtil;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.roydon.common.constant.CacheConstants.*;

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
    private RedisTemplate redisTemplate;

    @Resource
    private AppNewsMapper appNewsMapper;

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

//    @Async
//    @PostConstruct
//    public void init() {
//        // TODO 如果爬取新闻事件被触发，也要调用此方法重新将新数据写入缓存
//        // TODO 优化根据索引--两个字段
//        log.info("新闻浏览量写入缓存开始==>");
//        LambdaQueryWrapper<AppNews> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(AppNews::getNewsId, AppNews::getViewNum);
//        List<AppNews> appNewsList = list(queryWrapper);
//        Map<String, Integer> newsViewMap = appNewsList.stream().collect(Collectors.toMap(AppNews::getNewsId, AppNews::getViewNum));
//        redisCache.setCacheMap(NEWS_VIEW_NUM_KEY, newsViewMap);
//        redisCache.expire(NEWS_VIEW_NUM_KEY, NEWS_VIEW_NUM_KEY_TTL, TimeUnit.MINUTES);// 30分钟过期
//        log.info("<==新闻浏览量写入缓存成功");
//    }

    //=============================私有方法区=================
    private void getNewsViewNumFromRedis(AppNews appNews) {
        Integer viewNum = redisCache.getCacheMapValue(NEWS_VIEW_NUM_KEY, appNews.getNewsId());
        if (viewNum != null) {
            appNews.setViewNum(viewNum);
        }

    }

    private void getHotNewsViewNumFromRedis(HotNews hotNews) {
        Integer viewNum = redisCache.getCacheMapValue(NEWS_VIEW_NUM_KEY, hotNews.getNewsId());
        if (viewNum != null) {
            hotNews.setViewNum(viewNum);
        }
    }

    /**
     * 把一条新闻的浏览量缓存
     */
    private void setViewNumToCache(AppNews appNews) {
        Map<String, Integer> viewNumMap = new HashMap<>();
        viewNumMap.put(appNews.getNewsId(), appNews.getViewNum());
        redisCache.setCacheMap(NEWS_VIEW_NUM_KEY, viewNumMap);
        // 30分钟过期
        redisCache.expire(NEWS_VIEW_NUM_KEY, NEWS_VIEW_NUM_KEY_TTL, TimeUnit.MINUTES);
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
        setViewNumToCache(one);
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

    @Transactional
    @Override
    public boolean removeNewsByIds(String[] newsIds) {
        return removeBatchByIds(Arrays.asList(newsIds));
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
        // 互斥锁解决缓存击穿
        return getHotNewsWithMutex();
    }

    public List<HotNews> getHotNewsWithMutex() {
        // 从redis查询缓存
        String key = NEWS_HOT_NEWS;
        List<HotNews> cacheHotNewsList = redisCache.getCacheList(NEWS_HOT_NEWS);
        if (StringUtils.isNotEmpty(cacheHotNewsList)) {
            // 缓存存在就直接返回,从redis读取新闻浏览量
            cacheHotNewsList.forEach(this::getHotNewsViewNumFromRedis);
            return cacheHotNewsList;
        }
        // redis中不存在,实现缓存重建
        // 1、获取互斥锁
        String lockKey = NEWS_HOT_NEWS_LOCK_KEY;
        List<HotNews> hotNewsList = null;
        try {
            boolean isLock = tryLock(lockKey);
            // 2、判断是否获取成功
            if (!isLock) {//获取锁失败
                // 3、失败，休眠小时间后重试查询操作
                Thread.sleep(10);
                return getHotNewsWithMutex();
            }
            // 4、成功，查询数据库,存入redis
            hotNewsList = setHotNewsToCache();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放互斥锁
            unLock(lockKey);
        }
        return hotNewsList;
    }

    /**
     * 最近两天热点新闻10条写入缓存
     *
     * @return ids
     */
    @Override
    public List<HotNews> setHotNewsToCache() {
        log.info("热点新闻写入缓存==>");
        if (StringUtils.isNotEmpty(redisCache.getCacheList(NEWS_HOT_NEWS))) {
            redisCache.deleteObject(NEWS_HOT_NEWS);
        }
        // 筛选最近两天的数据
        LambdaQueryWrapper<AppNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(AppNews::getPostTime, DateUtils.getTimeBeforeDay(2L), LocalDateTime.now());
        // 按照浏览量降序
        queryWrapper.orderByDesc(AppNews::getViewNum);
        // limit十条
        IPage<AppNews> newsPage = page(new Page<>(0, 10), queryWrapper);
        List<AppNews> newsList = newsPage.getRecords();
        // AppNews转为HotNews存入redis
        List<HotNews> hotNewsList = BeanCopyUtils.copyBeanList(newsList, HotNews.class);
        redisCache.setCacheList(NEWS_HOT_NEWS, hotNewsList);
        // 30分钟过期
        redisCache.expire(NEWS_HOT_NEWS, NEWS_HOT_NEWS_TTL, TimeUnit.MINUTES);
        List<String> hotNewsIds = newsList.stream().map(AppNews::getNewsId).collect(Collectors.toList());
        log.info("==>hotNewsIds：{}", hotNewsIds);
        log.info("<==热点新闻写入缓存结束");
        return hotNewsList;
    }


    private boolean tryLock(String key) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, "1", CACHE_BUILD_LOCK_TTL, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    private void unLock(String key) {
        redisTemplate.delete(key);
    }

}




