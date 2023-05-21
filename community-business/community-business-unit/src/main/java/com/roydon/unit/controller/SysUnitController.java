package com.roydon.unit.controller;

import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.utils.StringUtils;
import com.roydon.unit.domain.entity.SysUnit;
import com.roydon.unit.service.ISysUnitService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 单元表(SysUnit)表控制层
 *
 * @author roydon
 * @since 2023-05-22 03:53:00
 */
@RestController
@RequestMapping("/system/unit")
public class SysUnitController {

    @Resource
    private ISysUnitService sysUnitService;

    /**
     * 获取部门列表
     */
    @ApiOperation("部门列表")
    @PreAuthorize("@ss.hasPermi('system:unit:list')")
    @GetMapping("/list")
    public AjaxResult list(SysUnit unit) {
        List<SysUnit> unitList = sysUnitService.selectUnitList(unit);
        return AjaxResult.success(unitList);
    }

    /**
     * 查询列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:unit:list')")
    @GetMapping("/list/exclude/{unitId}")
    public AjaxResult excludeChild(@PathVariable(value = "unitId", required = false) Long unitId) {
        List<SysUnit> unitList = sysUnitService.selectUnitList(new SysUnit());
        unitList.removeIf(d -> d.getUnitId().intValue() == unitId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), unitId + ""));
        return AjaxResult.success(unitList);
    }


}

