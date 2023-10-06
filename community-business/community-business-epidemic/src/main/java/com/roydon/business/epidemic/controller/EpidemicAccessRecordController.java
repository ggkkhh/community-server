package com.roydon.business.epidemic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.epidemic.domain.dto.EpidemicAccessRecordPageDTO;
import com.roydon.business.epidemic.domain.entity.EpidemicAccessRecord;
import com.roydon.business.epidemic.service.IEpidemicAccessRecordService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出入社区人员记录Controller
 *
 * @author roydon
 * @date 2023-05-26
 */
@RestController
@RequestMapping("/epidemic/access")
public class EpidemicAccessRecordController extends BaseController {

    @Resource
    private IEpidemicAccessRecordService epidemicAccessRecordService;

    /**
     * 我的出入社区报备记录
     */
    @PostMapping("/my_record")
    public AjaxResult myRecord(@RequestBody EpidemicAccessRecordPageDTO pageDTO) {
        IPage<EpidemicAccessRecord> iPage = epidemicAccessRecordService.getMyRecordListIPage(pageDTO);
        return AjaxResult.genTableData(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * 查询出入社区人员记录列表
     */
    @PreAuthorize("@ss.hasPermi('epidemic:access:list')")
    @GetMapping("/list")
    public TableDataInfo list(EpidemicAccessRecord epidemicAccessRecord) {
        startPage();
        List<EpidemicAccessRecord> list = epidemicAccessRecordService.selectEpidemicAccessRecordList(epidemicAccessRecord);
        return getDataTable(list);
    }

    /**
     * 导出出入社区人员记录列表
     */
    @PreAuthorize("@ss.hasPermi('epidemic:access:export')")
    @Log(title = "出入社区人员记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EpidemicAccessRecord epidemicAccessRecord) {
        List<EpidemicAccessRecord> list = epidemicAccessRecordService.selectEpidemicAccessRecordList(epidemicAccessRecord);
        ExcelUtil<EpidemicAccessRecord> util = new ExcelUtil<EpidemicAccessRecord>(EpidemicAccessRecord.class);
        util.exportExcel(response, list, "出入社区人员记录数据");
    }

    /**
     * 获取出入社区人员记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('epidemic:access:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") String recordId) {
        return success(epidemicAccessRecordService.selectEpidemicAccessRecordByRecordId(recordId));
    }

    /**
     * 新增出入社区人员记录
     */
    @Log(title = "出入社区人员记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpidemicAccessRecord epidemicAccessRecord) {
        return toAjax(epidemicAccessRecordService.insertEpidemicAccessRecord(epidemicAccessRecord));
    }

    /**
     * 修改出入社区人员记录
     */
    @PreAuthorize("@ss.hasPermi('epidemic:access:edit')")
    @Log(title = "出入社区人员记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpidemicAccessRecord epidemicAccessRecord) {
        return toAjax(epidemicAccessRecordService.updateEpidemicAccessRecord(epidemicAccessRecord));
    }

    /**
     * 删除出入社区人员记录
     */
    @PreAuthorize("@ss.hasPermi('epidemic:access:remove')")
    @Log(title = "出入社区人员记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable String[] recordIds) {
        return toAjax(epidemicAccessRecordService.deleteEpidemicAccessRecordByRecordIds(recordIds));
    }
}
