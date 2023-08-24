package com.roydon.admin.web.controller.system;

import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.poi.ExcelUtil;
import com.roydon.system.domain.entity.SysFeedback;
import com.roydon.system.service.ISysFeedbackService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统反馈Controller
 *
 * @author roydon
 * @date 2023-08-24
 */
@RestController
@RequestMapping("/system/feedback")
public class SysFeedbackController extends BaseController {
    @Resource
    private ISysFeedbackService sysFeedbackService;

    /**
     * 查询系统反馈列表
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFeedback sysFeedback) {
        startPage();
        List<SysFeedback> list = sysFeedbackService.selectSysFeedbackList(sysFeedback);
        return getDataTable(list);
    }

    /**
     * 导出系统反馈列表
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:export')")
    @Log(title = "系统反馈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFeedback sysFeedback) {
        List<SysFeedback> list = sysFeedbackService.selectSysFeedbackList(sysFeedback);
        ExcelUtil<SysFeedback> util = new ExcelUtil<SysFeedback>(SysFeedback.class);
        util.exportExcel(response, list, "系统反馈数据");
    }

    /**
     * 获取系统反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:query')")
    @GetMapping(value = "/{feedbackId}")
    public AjaxResult getInfo(@PathVariable("feedbackId") Long feedbackId) {
        return success(sysFeedbackService.selectSysFeedbackByFeedbackId(feedbackId));
    }

    /**
     * 新增系统反馈
     */
    @Log(title = "系统反馈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFeedback sysFeedback) {
        return toAjax(sysFeedbackService.insertSysFeedback(sysFeedback));
    }

    /**
     * 修改系统反馈
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:edit')")
    @Log(title = "系统反馈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFeedback sysFeedback) {
        return toAjax(sysFeedbackService.updateSysFeedback(sysFeedback));
    }

    /**
     * 删除系统反馈
     */
    @PreAuthorize("@ss.hasPermi('system:feedback:remove')")
    @Log(title = "系统反馈", businessType = BusinessType.DELETE)
    @DeleteMapping("/{feedbackIds}")
    public AjaxResult remove(@PathVariable Long[] feedbackIds) {
        return toAjax(sysFeedbackService.deleteSysFeedbackByFeedbackIds(feedbackIds));
    }

}
