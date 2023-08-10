package com.roydon.business.epidemic.controller;

import com.roydon.business.epidemic.domain.entity.EpidemicInoculationAudit;
import com.roydon.business.epidemic.service.IEpidemicInoculationAuditService;
import com.roydon.business.oss.service.OssService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.poi.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 疫苗接种审核Controller
 *
 * @author roydon
 * @date 2023-08-10
 */
@RestController
@RequestMapping("/epidemic/inoculation/audit")
public class EpidemicInoculationAuditController extends BaseController {

    @Resource
    private IEpidemicInoculationAuditService epidemicInoculationAuditService;

    @Resource
    private OssService ossService;

    /**
     * app端上传疫苗接种审核
     */
    @Log(title = "疫苗接种审核", businessType = BusinessType.INSERT)
    @PostMapping("/report")
    public AjaxResult report(@RequestBody EpidemicInoculationAudit epidemicInoculationAudit) {
        return toAjax(epidemicInoculationAuditService.insertEpidemicInoculationAudit(epidemicInoculationAudit));
    }

    /**
     * 头像上传
     */
    @ApiOperation("疫苗接种审核图片上传")
    @PostMapping("/upload-image")
    public AjaxResult avatar(@RequestParam("imagefile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String auditImg = ossService.uploadInoculationAuditImgFile(file);
            return AjaxResult.success().put("imgUrl", auditImg);
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 查询疫苗接种审核列表
     */
    @PreAuthorize("@ss.hasPermi('inoculation:audit:list')")
    @GetMapping("/list")
    public TableDataInfo list(EpidemicInoculationAudit epidemicInoculationAudit) {
        startPage();
        List<EpidemicInoculationAudit> list = epidemicInoculationAuditService.selectEpidemicInoculationAuditList(epidemicInoculationAudit);
        return getDataTable(list);
    }

    /**
     * 导出疫苗接种审核列表
     */
    @PreAuthorize("@ss.hasPermi('inoculation:audit:export')")
    @Log(title = "疫苗接种审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EpidemicInoculationAudit epidemicInoculationAudit) {
        List<EpidemicInoculationAudit> list = epidemicInoculationAuditService.selectEpidemicInoculationAuditList(epidemicInoculationAudit);
        ExcelUtil<EpidemicInoculationAudit> util = new ExcelUtil<EpidemicInoculationAudit>(EpidemicInoculationAudit.class);
        util.exportExcel(response, list, "疫苗接种审核数据");
    }

    /**
     * 获取疫苗接种审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('inoculation:audit:query')")
    @GetMapping(value = "/{auditId}")
    public AjaxResult getInfo(@PathVariable("auditId") Long auditId) {
        return success(epidemicInoculationAuditService.selectEpidemicInoculationAuditByAuditId(auditId));
    }

    /**
     * 新增疫苗接种审核
     */
    @PreAuthorize("@ss.hasPermi('inoculation:audit:add')")
    @Log(title = "疫苗接种审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpidemicInoculationAudit epidemicInoculationAudit) {
        return toAjax(epidemicInoculationAuditService.insertEpidemicInoculationAudit(epidemicInoculationAudit));
    }

    /**
     * 修改疫苗接种审核
     */
    @PreAuthorize("@ss.hasPermi('inoculation:audit:edit')")
    @Log(title = "疫苗接种审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpidemicInoculationAudit epidemicInoculationAudit) {
        return toAjax(epidemicInoculationAuditService.updateEpidemicInoculationAudit(epidemicInoculationAudit));
    }

    /**
     * 删除疫苗接种审核
     */
    @PreAuthorize("@ss.hasPermi('inoculation:audit:remove')")
    @Log(title = "疫苗接种审核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{auditIds}")
    public AjaxResult remove(@PathVariable Long[] auditIds) {
        return toAjax(epidemicInoculationAuditService.deleteEpidemicInoculationAuditByAuditIds(auditIds));
    }

}
