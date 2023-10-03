package com.roydon.business.epidemic.controller;

import com.roydon.business.epidemic.domain.entity.EpidemicIsolationRecord;
import com.roydon.business.epidemic.service.IEpidemicIsolationRecordService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 隔离记录Controller
 *
 * @author roydon
 * @date 2023-08-09
 */
@RestController
@RequestMapping("/epidemic/isolation")
public class EpidemicIsolationRecordController extends BaseController {

    @Resource
    private IEpidemicIsolationRecordService epidemicIsolationRecordService;

    /**
     * 我的隔离记录一条
     */
    @GetMapping("/unfinished_record")
    public AjaxResult myIsolationRecord() {
        EpidemicIsolationRecord unfinishedRecord = epidemicIsolationRecordService.getUnfinishedRecord(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return AjaxResult.success(unfinishedRecord);
    }

    /**
     * 查询隔离记录列表
     */
    @PreAuthorize("@ss.hasPermi('epidemic:isolation:list')")
    @GetMapping("/list")
    public TableDataInfo list(EpidemicIsolationRecord epidemicIsolationRecord) {
        startPage();
        List<EpidemicIsolationRecord> list = epidemicIsolationRecordService.selectEpidemicIsolationRecordList(epidemicIsolationRecord);
        return getDataTable(list);
    }

    /**
     * 导出隔离记录列表
     */
    @PreAuthorize("@ss.hasPermi('epidemic:isolation:export')")
    @Log(title = "隔离记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EpidemicIsolationRecord epidemicIsolationRecord) {
        List<EpidemicIsolationRecord> list = epidemicIsolationRecordService.selectEpidemicIsolationRecordList(epidemicIsolationRecord);
        ExcelUtil<EpidemicIsolationRecord> util = new ExcelUtil<EpidemicIsolationRecord>(EpidemicIsolationRecord.class);
        util.exportExcel(response, list, "隔离记录数据");
    }

    /**
     * 获取隔离记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('epidemic:isolation:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId) {
        return success(epidemicIsolationRecordService.selectEpidemicIsolationRecordByRecordId(recordId));
    }

    /**
     * 新增隔离记录
     */
    @PreAuthorize("@ss.hasPermi('epidemic:isolation:add')")
    @Log(title = "隔离记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpidemicIsolationRecord epidemicIsolationRecord) {
        epidemicIsolationRecordService.insertEpidemicIsolationRecord(epidemicIsolationRecord);
        return AjaxResult.success();
    }

    /**
     * 修改隔离记录
     */
    @PreAuthorize("@ss.hasPermi('epidemic:isolation:edit')")
    @Log(title = "隔离记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpidemicIsolationRecord epidemicIsolationRecord) {
        return toAjax(epidemicIsolationRecordService.updateEpidemicIsolationRecord(epidemicIsolationRecord));
    }

    /**
     * 删除隔离记录
     */
    @PreAuthorize("@ss.hasPermi('epidemic:isolation:remove')")
    @Log(title = "隔离记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds) {
        return toAjax(epidemicIsolationRecordService.deleteEpidemicIsolationRecordByRecordIds(recordIds));
    }
}
