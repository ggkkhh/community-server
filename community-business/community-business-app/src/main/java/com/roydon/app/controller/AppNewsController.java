package com.roydon.app.controller;

import com.roydon.app.domain.vo.NewsCategoryVO;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysDictData;
import com.roydon.common.utils.bean.BeanCopyUtils;
import com.roydon.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
public class AppNewsController extends BaseController {

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
