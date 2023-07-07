package com.roydon.quartz.task;

import com.roydon.business.news.httpclient.GetNewsService;
import com.roydon.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务调度测试
 *
 * @author roydon
 */
@Component("ryTask")
public class RyTask {

    @Resource
    private GetNewsService getNewsService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
//        NewsRespResult newsRespResult = getNewsService.getNewsRespResult();
        getNewsService.getNewsList();
//        System.out.println("newsRespResult = " + newsRespResult);
    }
}
