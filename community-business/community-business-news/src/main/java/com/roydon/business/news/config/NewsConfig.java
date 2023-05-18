package com.roydon.business.news.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * NewsConfig
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Component
public class NewsConfig {

    @Value("${roll.appId}")
    private String appid;

    @Value("${roll.appSecret}")
    private String appSecret;

    @Value("${roll.baseUrl}")
    private String baseUrl;

    public String getNewsListUrl(String typeId, Integer page) {
        return baseUrl + "list?typeId=" + typeId + "&page=" + page + "&app_id=" + appid + "&app_secret=" + appSecret;
    }

    public String getNewsDetailsUrl(String newsId) {
        return baseUrl + "details?newsId=" + newsId + "&app_id=" + appid + "&app_secret=" + appSecret;
    }

}
