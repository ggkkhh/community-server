package com.roydon.app.controller;

import com.roydon.business.news.domain.AppNews;
import com.roydon.business.news.domain.vo.NewsCategoryVO;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysDictData;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.bean.BeanCopyUtils;
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

    @ApiOperation("新闻分类")
    @GetMapping("/category")
    public AjaxResult getNewsDictList() {
        List<SysDictData> dataList = dictDataService.selectNewsDictList();
        List<NewsCategoryVO> newsCategoryVOList = BeanCopyUtils.copyBeanList(dataList, NewsCategoryVO.class);
        return AjaxResult.success(newsCategoryVOList);
    }

}
