package com.roydon.quartz.task;

import com.roydon.business.news.httpclient.GetNewsService;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.business.news.task.UpdateNewsViewNumTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * NewsTack
 *
 * @author: roydon
 * @date: 2023/5/13
 **/
@Component("newsTask")
public class NewsTask {

    @Resource
    private GetNewsService getNewsService;

    @Resource
    private AppNewsService appNewsService;

    @Resource
    private UpdateNewsViewNumTask updateNewsViewNumTask;

    /**
     * 定时拉取第三方新闻
     */
    public void updateNewsDaily() {
        getNewsService.getNewsList();
    }

    /**
     * 更新新闻浏览量
     */
    public void updateNewsViewNum() {
        updateNewsViewNumTask.updateViewCount();
    }

    /**
     * 缓存热点新闻
     */
    public void setHotNewsToCache() {
        appNewsService.setHotNewsToCache();
    }
}
