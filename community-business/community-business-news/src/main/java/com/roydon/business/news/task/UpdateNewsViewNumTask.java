package com.roydon.business.news.task;

import com.roydon.business.news.domain.AppNews;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.core.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * updateNewsViewNumTask
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/21
 **/
@Component
public class UpdateNewsViewNumTask {

    private static final Logger log = LoggerFactory.getLogger(UpdateNewsViewNumTask.class);

    @Resource
    private RedisCache redisCache;

    @Resource
    private AppNewsService appNewsService;

    @Scheduled(cron = "* 0/10 * * * ?")
    public void updateViewCount() {
        log.info("开始从redis更新新闻浏览量==>");
        //获取redis中的浏览量
        Map<String, Integer> viewNumMap = redisCache.getCacheMap(CacheConstants.NEWS_VIEW_NUM_KEY);
        List<AppNews> newsList = viewNumMap.entrySet().stream().map(entry -> {
            AppNews an = new AppNews();
            an.setNewsId(entry.getKey());
            an.setViewNum(entry.getValue());
            return an;
        }).collect(Collectors.toList());
        //更新数据库
        appNewsService.updateBatchById(newsList);
        log.info("<==新闻浏览量数据库与redis同步成功");
    }
}
