package com.roydon.quartz.task;

import com.roydon.business.news.httpclient.GetNewsService;
import com.roydon.business.news.task.UpdateNewsViewNumTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * NewsTack
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Component("newsTask")
public class NewsTask {

    @Resource
    private GetNewsService getNewsService;

    @Resource
    private UpdateNewsViewNumTask updateNewsViewNumTask;

    public void updateNewsDaily() {
        getNewsService.getNewsList();
    }

    public void updateNewsViewNum() {
        updateNewsViewNumTask.updateViewCount();
    }
}
