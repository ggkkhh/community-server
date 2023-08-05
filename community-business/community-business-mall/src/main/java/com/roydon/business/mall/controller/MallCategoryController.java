package com.roydon.business.mall.controller;

import com.roydon.business.mall.domain.entity.MallCategory;
import com.roydon.business.mall.service.IMallCategoryService;
import com.roydon.common.annotation.Log;
import com.roydon.common.constant.UserConstants;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtils;
import com.roydon.common.utils.poi.ExcelUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品分类Controller
 *
 * @author roydon
 * @date 2023-08-05
 */
@RestController
@RequestMapping("/mall/category")
public class MallCategoryController {

    @Resource
    private IMallCategoryService mallCategoryService;

    /**
     * 查询商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('mall:category:list')")
    @GetMapping("/list")
    public AjaxResult list(MallCategory mallCategory) {
        List<MallCategory> categoryList = mallCategoryService.selectCategoryList(mallCategory);
        return AjaxResult.success(categoryList);
    }

    /**
     * 查询列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('mall:category:list')")
    @GetMapping("/list/exclude/{categoryId}")
    public AjaxResult excludeChild(@PathVariable(value = "categoryId", required = false) Long categoryId) {
        List<MallCategory> categoryList = mallCategoryService.selectCategoryList(new MallCategory());
        categoryList.removeIf(d -> d.getCategoryId().intValue() == categoryId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), categoryId + ""));
        return AjaxResult.success(categoryList);
    }

    /**
     * 导出商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('mall:category:export')")
    @Log(title = "商品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallCategory mallCategory) {
        List<MallCategory> list = mallCategoryService.selectMallCategoryList(mallCategory);
        ExcelUtil<MallCategory> util = new ExcelUtil<MallCategory>(MallCategory.class);
        util.exportExcel(response, list, "商品分类数据");
    }

    /**
     * 获取商品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId) {
        return AjaxResult.success(mallCategoryService.getById(categoryId));
    }

    /**
     * 新增商品分类
     */
    @PreAuthorize("@ss.hasPermi('mall:category:add')")
    @Log(title = "商品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallCategory mallCategory) {
        if (UserConstants.NOT_UNIQUE.equals(mallCategoryService.checkCategoryNameUnique(mallCategory))) {
            return AjaxResult.error("新增分类：'" + mallCategory.getCategoryName() + "'失败，名称已存在");
        }
        mallCategory.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return toAjaxResult(mallCategoryService.insertCategory(mallCategory));
    }

    /**
     * 修改商品分类
     */
    @PreAuthorize("@ss.hasPermi('mall:category:edit')")
    @Log(title = "商品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallCategory mallCategory) {
        Long categoryId = mallCategory.getCategoryId();
        if (UserConstants.NOT_UNIQUE.equals(mallCategoryService.checkCategoryNameUnique(mallCategory))) {
            return AjaxResult.error("修改分类：'" + mallCategory.getCategoryName() + "'失败，分类名称已存在");
        } else if (mallCategory.getParentId().equals(categoryId)) {
            return AjaxResult.error("修改分类：'" + mallCategory.getCategoryName() + "'失败，上级不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, mallCategory.getStatus()) && mallCategoryService.selectNormalChildrenCategoryById(categoryId) > 0) {
            return AjaxResult.error("该分类包含未停用的子分类！");
        }
        mallCategory.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return toAjaxResult(mallCategoryService.updateById(mallCategory));
    }

    /**
     * 删除商品分类
     */
    @PreAuthorize("@ss.hasPermi('mall:category:remove')")
    @Log(title = "商品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryId}")
    public AjaxResult remove(@PathVariable Long categoryId) {
        if (mallCategoryService.hasChildByCategoryId(categoryId)) {
            return AjaxResult.error("存在下级,不允许删除");
        }
//        if (mallCategoryService.checkUnitExistGoods(categoryId)) {
//            return AjaxResult.error("存在商品,不允许删除");
//        }
        return toAjaxResult(mallCategoryService.removeById(categoryId));
    }

    private AjaxResult toAjaxResult(boolean b) {
        return b ? AjaxResult.success() : AjaxResult.error();
    }

    private AjaxResult toAjaxResult(int b) {
        return b == 1 ? AjaxResult.success() : AjaxResult.error();
    }

}
