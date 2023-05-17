package com.roydon.business.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.news.domain.AppNews;
import com.roydon.business.news.mapper.AppNewsMapper;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.common.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author roydon
 * @description 针对表【app_news】的数据库操作Service实现
 * @createDate 2023-05-13 13:51:44
 */
@Service
public class AppNewsServiceImpl extends ServiceImpl<AppNewsMapper, AppNews> implements AppNewsService {

    @Override
    public List<AppNews> getNewsList(AppNews appNews) {
        LambdaQueryWrapper<AppNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotEmpty(appNews.getNewsTitle()), AppNews::getNewsTitle, appNews.getNewsTitle())
                .like(StringUtil.isNotEmpty(appNews.getSource()), AppNews::getSource, appNews.getSource())
                .eq(StringUtil.isNotEmpty(appNews.getNewsType()), AppNews::getNewsType, appNews.getNewsType())
                .eq(StringUtil.isNotEmpty(appNews.getDelFlag()), AppNews::getDelFlag, appNews.getDelFlag())
                .between(StringUtil.isNotEmpty(appNews.getParams().get("beginTime")) || StringUtil.isNotEmpty(appNews.getParams().get("endTime")), AppNews::getPostTime, appNews.getParams().get("beginTime"), appNews.getParams().get("endTime"))
                .orderByDesc(AppNews::getPostTime);
        return list(queryWrapper);
    }

    @Override
    public AppNews getNewsDetails(String newsId) {
        LambdaQueryWrapper<AppNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtil.isNotEmpty(newsId), AppNews::getNewsId, newsId);
        return getOne(queryWrapper);
    }
}




