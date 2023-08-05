package com.roydon.unit.controller;

import com.roydon.common.annotation.Log;
import com.roydon.common.constant.UserConstants;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtils;
import com.roydon.unit.domain.entity.SysUnit;
import com.roydon.unit.service.ISysUnitService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 获取list
     */
    @ApiOperation("单元列表")
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

    /**
     * 获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:unit:query')")
    @GetMapping(value = "/{unitId}")
    public AjaxResult getInfo(@PathVariable Long unitId) {
        sysUnitService.checkUnitDataScope(unitId);
        return AjaxResult.success(sysUnitService.selectUnitById(unitId));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('system:unit:add')")
    @Log(title = "单元管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUnit unit) {
        if (UserConstants.NOT_UNIQUE.equals(sysUnitService.checkUnitNameUnique(unit))) {
            return AjaxResult.error("新增单元'" + unit.getUnitName() + "'失败，单元名称已存在");
        }
        unit.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return toAjaxResult(sysUnitService.insertUnit(unit));
    }

    /**
     * 修改
     */
    @PreAuthorize("@ss.hasPermi('system:unit:edit')")
    @Log(title = "单元管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUnit unit) {
        Long unitId = unit.getUnitId();
        sysUnitService.checkUnitDataScope(unitId);
        if (UserConstants.NOT_UNIQUE.equals(sysUnitService.checkUnitNameUnique(unit))) {
            return AjaxResult.error("修改单元'" + unit.getUnitName() + "'失败，单元名称已存在");
        } else if (unit.getParentId().equals(unitId)) {
            return AjaxResult.error("修改单元'" + unit.getUnitName() + "'失败，上级单元不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, unit.getStatus()) && sysUnitService.selectNormalChildrenUnitById(unitId) > 0) {
            return AjaxResult.error("该单元包含未停用的子单元！");
        }
        unit.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return toAjaxResult(sysUnitService.updateById(unit));
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('system:unit:remove')")
    @Log(title = "单元管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{unitId}")
    public AjaxResult remove(@PathVariable Long unitId) {
        if (sysUnitService.hasChildByUnitId(unitId)) {
            return AjaxResult.error("存在下级,不允许删除");
        }
//        if (sysUnitService.checkUnitExistUser(unitId)) {
//            return AjaxResult.error("存在用户,不允许删除");
//        }
        sysUnitService.checkUnitDataScope(unitId);
        return toAjaxResult(sysUnitService.removeById(unitId));
    }

    private AjaxResult toAjaxResult(boolean b) {
        return b ? AjaxResult.success() : AjaxResult.error();
    }

}

