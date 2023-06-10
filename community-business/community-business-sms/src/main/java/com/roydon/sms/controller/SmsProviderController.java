package com.roydon.sms.controller;

import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.poi.ExcelUtil;
import com.roydon.sms.domain.entity.SmsProvider;
import com.roydon.sms.service.ISmsProviderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统短信服务供应商(SmsProvider)表控制层
 *
 * @author roydon
 * @since 2023-05-24 19:07:28
 */
@RestController
@RequestMapping("/sms/provider")
public class SmsProviderController extends BaseController {

    @Resource
    private ISmsProviderService smsProviderService;

    /**
     * 查询系统短信服务供应商列表
     */
    @PreAuthorize("@ss.hasPermi('sms:provider:list')")
    @GetMapping("/list")
    public TableDataInfo list(SmsProvider smsProvider) {
        startPage();
        List<SmsProvider> list = smsProviderService.selectSmsProviderList(smsProvider);
        return getDataTable(list);
    }

    /**
     * 导出系统短信服务供应商列表
     */
    @PreAuthorize("@ss.hasPermi('sms:provider:export')")
    @Log(title = "系统短信服务供应商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SmsProvider smsProvider) {
        List<SmsProvider> list = smsProviderService.selectSmsProviderList(smsProvider);
        ExcelUtil<SmsProvider> util = new ExcelUtil<SmsProvider>(SmsProvider.class);
        util.exportExcel(response, list, "系统短信服务供应商数据");
    }

    /**
     * 获取系统短信服务供应商详细信息
     */
    @PreAuthorize("@ss.hasPermi('sms:provider:query')")
    @GetMapping(value = "/{providerId}")
    public AjaxResult getInfo(@PathVariable("providerId") String providerId) {
        return success(smsProviderService.selectSmsProviderByProviderId(providerId));
    }

    /**
     * 新增系统短信服务供应商
     */
    @PreAuthorize("@ss.hasPermi('sms:provider:add')")
    @Log(title = "系统短信服务供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SmsProvider smsProvider) {
        return toAjax(smsProviderService.insertSmsProvider(smsProvider));
    }

    /**
     * 修改系统短信服务供应商
     */
    @PreAuthorize("@ss.hasPermi('sms:provider:edit')")
    @Log(title = "系统短信服务供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SmsProvider smsProvider) {
        return toAjax(smsProviderService.updateSmsProvider(smsProvider));
    }

    /**
     * 删除系统短信服务供应商
     */
    @PreAuthorize("@ss.hasPermi('sms:provider:remove')")
    @Log(title = "系统短信服务供应商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{providerIds}")
    public AjaxResult remove(@PathVariable String[] providerIds) {
        return toAjax(smsProviderService.deleteSmsProviderByProviderIds(providerIds));
    }
}
