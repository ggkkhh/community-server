package com.roydon.app.controller;

import com.roydon.app.domain.entity.AppNotice;
import com.roydon.app.service.IAppNoticeService;
import com.roydon.business.oss.service.OssService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * app端图文轮播图公告(AppNotice)表控制层
 *
 * @author roydon
 * @since 2023-06-13 22:10:43
 */
@RestController
@RequestMapping("/app/notice")
public class AppNoticeController extends BaseController {

    @Resource
    private IAppNoticeService appNoticeService;

    @Resource
    private OssService ossService;

    /**
     * 查询app端图文轮播图公告列表
     */
    @ApiOperation("轮播图list")
    @PreAuthorize("@ss.hasPermi('app:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppNotice appNotice) {
        startPage();
        List<AppNotice> list = appNoticeService.getAppNoticeList(appNotice);
        return getDataTable(list);
    }

    /**
     * app端图文轮播图公告列表
     */
    @ApiOperation("app轮播图list")
    @GetMapping("/banner")
    public TableDataInfo banner(AppNotice appNotice) {
        startPage();
        List<AppNotice> list = appNoticeService.getAppBanner(appNotice);
        return getDataTable(list);
    }

    /**
     * 轮播图上传
     */
    @ApiOperation("轮播图上传")
    @Log(title = "轮播图上传", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('app:notice:edit')")
    @PostMapping("/uploadImg")
    public AjaxResult avatar(@RequestParam("appNoticeFile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String imgUrl = ossService.uploadNoticeFile(file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", imgUrl);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    @ApiOperation("公告状态")
    @Log(title = "公告管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('app:notice:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody AppNotice appNotice) {
        return toAjax(appNoticeService.changeStatus(appNotice));
    }

    /**
     * 获取app端图文轮播图公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('app:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId) {
        return success(appNoticeService.selectAppNoticeByNoticeId(noticeId));
    }

    /**
     * 新增app端图文轮播图公告
     */
    @PreAuthorize("@ss.hasPermi('app:notice:add')")
    @Log(title = "app端图文轮播图公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppNotice appNotice) {
        return toAjax(appNoticeService.insertAppNotice(appNotice));
    }

    /**
     * 修改app端图文轮播图公告
     */
    @PreAuthorize("@ss.hasPermi('app:notice:edit')")
    @Log(title = "app端图文轮播图公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppNotice appNotice) {
        return toAjax(appNoticeService.updateAppNotice(appNotice));
    }

    /**
     * 删除app端图文轮播图公告
     */
    @PreAuthorize("@ss.hasPermi('app:notice:remove')")
    @Log(title = "app端图文轮播图公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(appNoticeService.deleteAppNoticeByNoticeIds(noticeIds));
    }
}
