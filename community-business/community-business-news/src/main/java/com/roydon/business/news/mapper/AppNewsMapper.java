package com.roydon.business.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.news.domain.AppNews;

import java.util.List;

/**
 * @author roydon
 * @description 针对表【app_news】的数据库操作Mapper
 * @createDate 2023-05-13 13:51:44
 */
public interface AppNewsMapper extends BaseMapper<AppNews> {

    /**
     * 查询新闻
     *
     * @param newsId 新闻主键
     * @return 新闻
     */
    AppNews selectAppNewsByNewsId(String newsId);

    /**
     * 查询新闻列表
     *
     * @param appNews 新闻
     * @return 新闻集合
     */
    List<AppNews> selectAppNewsList(AppNews appNews);

    /**
     * 新增新闻
     *
     * @param appNews 新闻
     * @return 结果
     */
    int insertAppNews(AppNews appNews);

    /**
     * 修改新闻
     *
     * @param appNews 新闻
     * @return 结果
     */
    int updateAppNews(AppNews appNews);

    /**
     * 删除新闻
     *
     * @param newsId 新闻主键
     * @return 结果
     */
    int deleteAppNewsByNewsId(String newsId);

    /**
     * 批量删除新闻
     *
     * @param newsIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAppNewsByNewsIds(String[] newsIds);

}




