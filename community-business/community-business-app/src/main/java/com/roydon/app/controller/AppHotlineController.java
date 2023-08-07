package com.roydon.app.controller;

import com.roydon.app.domain.entity.AppHotline;
import com.roydon.app.service.IAppHotlineService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.poi.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 热线管理Controller
 *
 * @author roydon
 * @date 2023-08-07
 */
@RestController
@RequestMapping("/app/hotline")
public class AppHotlineController extends BaseController {

    @Resource
    private IAppHotlineService appHotlineService;

    /**
     * app接口：热线列表
     */
    @GetMapping("/all")
    public TableDataInfo all(AppHotline appHotline) {
        startPage();
        List<AppHotline> list = appHotlineService.selectAppHotlineListForApp(appHotline);
        return getDataTable(list);
    }

    /**
     * 查询热线管理列表
     */
    @PreAuthorize("@ss.hasPermi('app:hotline:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppHotline appHotline) {
        startPage();
        List<AppHotline> list = appHotlineService.selectAppHotlineList(appHotline);
        return getDataTable(list);
    }

    /**
     * 导出热线管理列表
     */
    @PreAuthorize("@ss.hasPermi('app:hotline:export')")
    @Log(title = "热线管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppHotline appHotline) {
        List<AppHotline> list = appHotlineService.selectAppHotlineList(appHotline);
        ExcelUtil<AppHotline> util = new ExcelUtil<AppHotline>(AppHotline.class);
        util.exportExcel(response, list, "热线数据");
    }

    /**
     * 获取热线管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('app:hotline:query')")
    @GetMapping(value = "/{hotlineId}")
    public AjaxResult getInfo(@PathVariable("hotlineId") Long hotlineId) {
        return success(appHotlineService.selectAppHotlineByHotlineId(hotlineId));
    }

    /**
     * 新增热线管理
     */
    @PreAuthorize("@ss.hasPermi('app:hotline:add')")
    @Log(title = "热线管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppHotline appHotline) {
        return toAjax(appHotlineService.insertAppHotline(appHotline));
    }

    /**
     * 修改热线管理
     */
    @PreAuthorize("@ss.hasPermi('app:hotline:edit')")
    @Log(title = "热线管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppHotline appHotline) {
        return toAjax(appHotlineService.updateAppHotline(appHotline));
    }

    @ApiOperation("热线状态")
    @Log(title = "热线管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('app:hotline:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody AppHotline appHotline) {
        return toAjax(appHotlineService.changeStatus(appHotline));
    }

    /**
     * 删除热线管理
     */
    @PreAuthorize("@ss.hasPermi('app:hotline:remove')")
    @Log(title = "热线管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{hotlineIds}")
    public AjaxResult remove(@PathVariable Long[] hotlineIds) {
        return toAjax(appHotlineService.deleteAppHotlineByHotlineIds(hotlineIds));
    }
}
