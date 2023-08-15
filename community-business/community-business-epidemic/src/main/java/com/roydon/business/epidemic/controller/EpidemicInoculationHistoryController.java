package com.roydon.business.epidemic.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roydon.business.epidemic.domain.entity.EpidemicInoculationAudit;
import com.roydon.business.epidemic.domain.entity.EpidemicInoculationHistory;
import com.roydon.business.epidemic.enums.AuditStatusEnum;
import com.roydon.business.epidemic.service.IEpidemicInoculationAuditService;
import com.roydon.business.epidemic.service.IEpidemicInoculationHistoryService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 疫苗接种记录Controller
 *
 * @author roydon
 * @date 2023-08-15
 */
@RestController
@RequestMapping("/epidemic/inoculation/history")
public class EpidemicInoculationHistoryController extends BaseController {

    @Resource
    private IEpidemicInoculationHistoryService epidemicInoculationHistoryService;

    @Resource
    private IEpidemicInoculationAuditService epidemicInoculationAuditService;

    /**
     * 查询用户的接种记录
     */
    @GetMapping("/user")
    public AjaxResult getByUser() {
        LambdaQueryWrapper<EpidemicInoculationHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EpidemicInoculationHistory::getUserId, SecurityUtils.getUserId());
        EpidemicInoculationHistory history = epidemicInoculationHistoryService.getOne(queryWrapper);
        if (StringUtil.isEmpty(history)) {
            // 查不到记录，就查询审核表
            LambdaQueryWrapper<EpidemicInoculationAudit> auditQueryWrapper = new LambdaQueryWrapper<>();
            auditQueryWrapper.eq(EpidemicInoculationAudit::getUserId, SecurityUtils.getUserId());
            EpidemicInoculationAudit audit = epidemicInoculationAuditService.getOne(auditQueryWrapper);
            if (StringUtil.isEmpty(audit)) {
                AjaxResult.success("还未上报接种记录");
            }
            if (audit.getAuditStatus().equals(AuditStatusEnum.NOT_PASS.getCode())) {
                AjaxResult.success("您上报的记录未审核通过，请重试");
            }
        }
        return AjaxResult.success(history);
    }

    /**
     * 查询疫苗接种记录列表
     */
    @PreAuthorize("@ss.hasPermi('inoculation:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(EpidemicInoculationHistory epidemicInoculationHistory) {
        startPage();
        List<EpidemicInoculationHistory> list = epidemicInoculationHistoryService.selectEpidemicInoculationHistoryList(epidemicInoculationHistory);
        return getDataTable(list);
    }

    /**
     * 导出疫苗接种记录列表
     */
    @PreAuthorize("@ss.hasPermi('inoculation:history:export')")
    @Log(title = "疫苗接种记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EpidemicInoculationHistory epidemicInoculationHistory) {
        List<EpidemicInoculationHistory> list = epidemicInoculationHistoryService.selectEpidemicInoculationHistoryList(epidemicInoculationHistory);
        ExcelUtil<EpidemicInoculationHistory> util = new ExcelUtil<EpidemicInoculationHistory>(EpidemicInoculationHistory.class);
        util.exportExcel(response, list, "疫苗接种记录数据");
    }

    /**
     * 获取疫苗接种记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('inoculation:history:query')")
    @GetMapping(value = "/{historyId}")
    public AjaxResult getInfo(@PathVariable("historyId") Long historyId) {
        return success(epidemicInoculationHistoryService.selectEpidemicInoculationHistoryByHistoryId(historyId));
    }

    /**
     * 新增疫苗接种记录
     */
    @PreAuthorize("@ss.hasPermi('inoculation:history:add')")
    @Log(title = "疫苗接种记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpidemicInoculationHistory epidemicInoculationHistory) {
        return toAjax(epidemicInoculationHistoryService.insertEpidemicInoculationHistory(epidemicInoculationHistory));
    }

    /**
     * 修改疫苗接种记录
     */
    @PreAuthorize("@ss.hasPermi('inoculation:history:edit')")
    @Log(title = "疫苗接种记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpidemicInoculationHistory epidemicInoculationHistory) {
        return toAjax(epidemicInoculationHistoryService.updateEpidemicInoculationHistory(epidemicInoculationHistory));
    }

    /**
     * 删除疫苗接种记录
     */
    @PreAuthorize("@ss.hasPermi('inoculation:history:remove')")
    @Log(title = "疫苗接种记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{historyIds}")
    public AjaxResult remove(@PathVariable Long[] historyIds) {
        return toAjax(epidemicInoculationHistoryService.deleteEpidemicInoculationHistoryByHistoryIds(historyIds));
    }
}
