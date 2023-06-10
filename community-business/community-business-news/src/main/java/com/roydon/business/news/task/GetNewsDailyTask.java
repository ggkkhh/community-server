package com.roydon.business.news.task;

import com.roydon.business.news.httpclient.GetNewsService;
import com.roydon.business.news.model.NewsRespResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每日获取新闻的定时任务
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Component("getNewsDailyTask")
public class GetNewsDailyTask {

    @Resource
    private GetNewsService getNewsService;

    public void getNewsList() {
        NewsRespResult newsRespResult = getNewsService.getNewsRespResult();
        System.out.println("newsRespResult = " + newsRespResult);
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }
}
