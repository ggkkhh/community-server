package com.roydon.business.epidemic.controller;

import com.roydon.business.epidemic.domain.entity.EpidemicIsolationPolicy;
import com.roydon.business.epidemic.service.IEpidemicIsolationPolicyService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.enums.BusinessType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 隔离政策Controller
 *
 * @author roydon
 * @date 2023-08-21
 */
@RestController
@RequestMapping("/epidemic/isolation/policy")
public class EpidemicIsolationPolicyController extends BaseController {

    @Resource
    private IEpidemicIsolationPolicyService epidemicIsolationPolicyService;

    /**
     * 获取隔离政策详细信息
     */
    @PreAuthorize("@ss.hasPermi('epidemic:isolation:edit')")
    @GetMapping(value = "/{policyId}")
    public AjaxResult getInfo(@PathVariable("policyId") Long policyId) {
        return success(epidemicIsolationPolicyService.selectEpidemicIsolationPolicyByPolicyId(policyId));
    }

    /**
     * 新增隔离政策
     */
    @PreAuthorize("@ss.hasPermi('epidemic:isolation:edit')")
    @Log(title = "隔离政策", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpidemicIsolationPolicy epidemicIsolationPolicy) {
        return toAjax(epidemicIsolationPolicyService.insertEpidemicIsolationPolicy(epidemicIsolationPolicy));
    }

    /**
     * 修改隔离政策
     */
    @PreAuthorize("@ss.hasPermi('epidemic:isolation:edit')")
    @Log(title = "隔离政策", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpidemicIsolationPolicy epidemicIsolationPolicy) {
        return toAjax(epidemicIsolationPolicyService.updateEpidemicIsolationPolicy(epidemicIsolationPolicy));
    }

}
