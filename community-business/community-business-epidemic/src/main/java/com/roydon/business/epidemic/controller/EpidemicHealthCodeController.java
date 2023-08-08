package com.roydon.business.epidemic.controller;

import com.roydon.business.epidemic.domain.entity.EpidemicHealthCode;
import com.roydon.business.epidemic.service.IEpidemicHealthCodeService;
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
 * 居民健康码Controller
 *
 * @author roydon
 * @date 2023-08-08
 */
@RestController
@RequestMapping("/epidemic/health")
public class EpidemicHealthCodeController extends BaseController {

    @Resource
    private IEpidemicHealthCodeService epidemicHealthCodeService;

    /**
     * app端查看健康码
     */
    @GetMapping("/code")
    public AjaxResult getCode() {
        return success(epidemicHealthCodeService.getHealthCodeByUser(getLoginUser().getUser()));
    }

    /**
     * 查询居民健康码列表
     */
    @PreAuthorize("@ss.hasPermi('epidemic:health:list')")
    @GetMapping("/list")
    public TableDataInfo list(EpidemicHealthCode epidemicHealthCode) {
        startPage();
        List<EpidemicHealthCode> list = epidemicHealthCodeService.selectEpidemicHealthCodeList(epidemicHealthCode);
        return getDataTable(list);
    }

    /**
     * 导出居民健康码列表
     */
    @PreAuthorize("@ss.hasPermi('epidemic:health:export')")
    @Log(title = "居民健康码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EpidemicHealthCode epidemicHealthCode) {
        List<EpidemicHealthCode> list = epidemicHealthCodeService.selectEpidemicHealthCodeList(epidemicHealthCode);
        ExcelUtil<EpidemicHealthCode> util = new ExcelUtil<EpidemicHealthCode>(EpidemicHealthCode.class);
        util.exportExcel(response, list, "居民健康码数据");
    }

    /**
     * 获取居民健康码详细信息
     */
    @PreAuthorize("@ss.hasPermi('epidemic:health:query')")
    @GetMapping(value = "/{codeId}")
    public AjaxResult getInfo(@PathVariable("codeId") Long codeId) {
        return success(epidemicHealthCodeService.selectEpidemicHealthCodeByCodeId(codeId));
    }

    /**
     * 新增居民健康码
     */
    @PreAuthorize("@ss.hasPermi('epidemic:health:add')")
    @Log(title = "居民健康码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpidemicHealthCode epidemicHealthCode) {
        return toAjax(epidemicHealthCodeService.insertEpidemicHealthCode(epidemicHealthCode));
    }

    /**
     * 修改居民健康码
     */
    @PreAuthorize("@ss.hasPermi('epidemic:health:edit')")
    @Log(title = "居民健康码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpidemicHealthCode epidemicHealthCode) {
        return toAjax(epidemicHealthCodeService.updateEpidemicHealthCode(epidemicHealthCode));
    }

    /**
     * 删除居民健康码
     */
    @PreAuthorize("@ss.hasPermi('epidemic:health:remove')")
    @Log(title = "居民健康码", businessType = BusinessType.DELETE)
    @DeleteMapping("/{codeIds}")
    public AjaxResult remove(@PathVariable Long[] codeIds) {
        return toAjax(epidemicHealthCodeService.deleteEpidemicHealthCodeByCodeIds(codeIds));
    }
}
