package com.roydon.business.news.controller;

import com.roydon.business.news.domain.AppNews;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysDictData;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * NewsController
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Api("新闻管理")
@RestController
@RequestMapping("/app/news")
public class NewsController extends BaseController {

    @Resource
    private AppNewsService appNewsService;

    @Resource
    private ISysDictDataService dictDataService;

    @PreAuthorize("@ss.hasPermi('app:news:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppNews appNews) {
        startPage();
        List<AppNews> list = appNewsService.getNewsList(appNews);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('app:news:query')")
    @Transactional
    @GetMapping("/{newsId}")
    public AjaxResult getDetails(@PathVariable(value = "newsId") String newsId) {
        AppNews appNews = appNewsService.getNewsDetails(newsId);
        // 新闻阅读量加一
        appNewsService.viewNumIncrease(newsId);
        return AjaxResult.success(appNews);
    }

    @ApiOperation("新闻状态")
    @Log(title = "新闻管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('app:news:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody AppNews appNews) {
        return toAjax(appNewsService.changeNewsStatus(appNews));
    }

    @ApiOperation("新闻删除")
    @PreAuthorize("@ss.hasPermi('app:news:remove')")
    @Log(title = "新闻管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{newsIds}")
    public AjaxResult remove(@PathVariable String[] newsIds) {
        return toAjax(appNewsService.removeNewsByIds(newsIds));
    }

    @ApiOperation("新闻修改")
    @PreAuthorize("@ss.hasPermi('app:news:edit')")
    @Log(title = "新闻管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public AjaxResult edit(@RequestBody AppNews appNews) {
        return toAjax(appNewsService.editNews(appNews));
    }

    @ApiOperation("新闻分类")
    @Log(title = "新闻管理", businessType = BusinessType.UPDATE)
    @GetMapping("/category")
    public AjaxResult getNewsDictList() {
        List<SysDictData> dataList = dictDataService.selectNewsDictList();
        return AjaxResult.success(dataList);
    }

}
