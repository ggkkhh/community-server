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

    /**
     * 获取新闻集合
     *
     * @param appNews
     * @return
     */
    List<AppNews> getNewsList(AppNews appNews);

    /**
     * 获取新闻详情
     *
     * @param newsId
     * @return
     */
    AppNews getNewsDetails(String newsId);

    /**
     * 改变新闻状态
     *
     * @param appNews
     * @return
     */
    boolean changeNewsStatus(AppNews appNews);

    /**
     * 删除（批量）
     *
     * @param newsIds
     * @return
     */
    boolean removeNewsByIds(String[] newsIds);

    /**
     * 修改新闻
     *
     * @param appNews
     * @return
     */
    boolean editNews(AppNews appNews);

    /**
     * 新闻浏览量加一业务
     *
     * @param newsId
     */
    void viewNumIncrease(String newsId);

//    boolean isExistById(String newsId);

}
