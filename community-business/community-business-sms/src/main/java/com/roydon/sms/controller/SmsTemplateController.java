package com.roydon.sms.controller;

import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.poi.ExcelUtil;
import com.roydon.sms.domain.entity.SmsTemplate;
import com.roydon.sms.service.ISmsTemplateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (SmsTemplate)表控制层
 *
 * @author roydon
 * @since 2023-05-24 19:08:58
 */
@RestController
@RequestMapping("/sms/template")
public class SmsTemplateController extends BaseController {
    @Resource
    private ISmsTemplateService smsTemplateService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('sms:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(SmsTemplate smsTemplate) {
        startPage();
        List<SmsTemplate> list = smsTemplateService.selectSmsTemplateList(smsTemplate);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('sms:template:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SmsTemplate smsTemplate) {
        List<SmsTemplate> list = smsTemplateService.selectSmsTemplateList(smsTemplate);
        ExcelUtil<SmsTemplate> util = new ExcelUtil<SmsTemplate>(SmsTemplate.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('sms:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") String templateId) {
        return success(smsTemplateService.selectSmsTemplateByTemplateId(templateId));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('sms:template:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SmsTemplate smsTemplate) {
        return toAjax(smsTemplateService.insertSmsTemplate(smsTemplate));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('sms:template:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SmsTemplate smsTemplate) {
        return toAjax(smsTemplateService.updateSmsTemplate(smsTemplate));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('sms:template:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable String[] templateIds) {
        return toAjax(smsTemplateService.deleteSmsTemplateByTemplateIds(templateIds));
    }
}
