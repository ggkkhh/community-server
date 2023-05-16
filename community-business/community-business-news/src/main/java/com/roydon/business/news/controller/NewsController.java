package com.roydon.business.news.controller;

import com.roydon.business.news.domain.AppNews;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.page.TableDataInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * NewsController
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@RestController
@RequestMapping("/app/news")
public class NewsController extends BaseController {

    @Resource
    private AppNewsService appNewsService;

    @PreAuthorize("@ss.hasPermi('app:news:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppNews appNews) {
        startPage();
        List<AppNews> list = appNewsService.selectNewsList(appNews);
        return getDataTable(list);
    }

}
