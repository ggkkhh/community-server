package com.roydon.business.news.service;

import com.roydon.business.news.domain.AppNews;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author roydon
* @description 针对表【app_news】的数据库操作Service
* @createDate 2023-05-13 13:51:44
*/
public interface AppNewsService extends IService<AppNews> {

    List<AppNews> getNewsList(AppNews appNews);

    AppNews getNewsDetails(String newsId);

    boolean changeNewsStatus(AppNews appNews);

}
