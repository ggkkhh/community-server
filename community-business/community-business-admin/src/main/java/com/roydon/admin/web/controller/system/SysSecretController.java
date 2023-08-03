package com.roydon.admin.web.controller.system;

import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.poi.ExcelUtil;
import com.roydon.system.domain.SysSecret;
import com.roydon.system.service.ISysSecretService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.roydon.common.annotation.Log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 密钥Controller
 *
 * @author roydon
 * @date 2023-08-03
 */
@Api("密钥管理")
@RestController
@RequestMapping("/system/secret")
public class SysSecretController extends BaseController {

    @Resource
    private ISysSecretService sysSecretService;

    /**
     * 查询密钥列表
     */
    @PreAuthorize("@ss.hasPermi('system:secret:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSecret sysSecret) {
        startPage();
        List<SysSecret> list = sysSecretService.selectSysSecretList(sysSecret);
        return getDataTable(list);
    }

    /**
     * 导出密钥列表
     */
    @PreAuthorize("@ss.hasPermi('system:secret:export')")
    @Log(title = "密钥", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSecret sysSecret) {
        List<SysSecret> list = sysSecretService.selectSysSecretList(sysSecret);
        ExcelUtil<SysSecret> util = new ExcelUtil<SysSecret>(SysSecret.class);
        util.exportExcel(response, list, "密钥数据");
    }

    /**
     * 获取密钥详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:secret:query')")
    @GetMapping(value = "/{secretId}")
    public AjaxResult getInfo(@PathVariable("secretId") Long secretId) {
        return success(sysSecretService.selectSysSecretBySecretId(secretId));
    }

    /**
     * 新增密钥
     */
    @PreAuthorize("@ss.hasPermi('system:secret:add')")
    @Log(title = "密钥", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSecret sysSecret) {
        return toAjax(sysSecretService.insertSysSecret(sysSecret));
    }

    /**
     * 修改密钥
     */
    @PreAuthorize("@ss.hasPermi('system:secret:edit')")
    @Log(title = "密钥", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSecret sysSecret) {
        return toAjax(sysSecretService.updateSysSecret(sysSecret));
    }

    /**
     * 删除密钥
     */
    @PreAuthorize("@ss.hasPermi('system:secret:remove')")
    @Log(title = "密钥", businessType = BusinessType.DELETE)
    @DeleteMapping("/{secretIds}")
    public AjaxResult remove(@PathVariable Long[] secretIds) {
        return toAjax(sysSecretService.deleteSysSecretBySecretIds(secretIds));
    }
}
