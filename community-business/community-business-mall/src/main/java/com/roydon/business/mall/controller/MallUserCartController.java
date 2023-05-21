package com.roydon.business.mall.controller;

import com.roydon.business.mall.domain.entity.MallUserCart;
import com.roydon.business.mall.service.IMallUserCartService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (MallUserCart)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:14:32
 */
@RestController
@RequestMapping("/app/mallUserCart")
public class MallUserCartController {

    @Resource
    private IMallUserCartService mallUserCartService;

    /**
     * 分页查询
     *
     * @param mallUserCart 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public AjaxResult queryByPage(MallUserCart mallUserCart, PageRequest pageRequest) {
        return AjaxResult.success(this.mallUserCartService.queryByPage(mallUserCart, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.mallUserCartService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param mallUserCart 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(MallUserCart mallUserCart) {
        return AjaxResult.success(this.mallUserCartService.insert(mallUserCart));
    }

    /**
     * 编辑数据
     *
     * @param mallUserCart 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MallUserCart mallUserCart) {
        return AjaxResult.success(this.mallUserCartService.update(mallUserCart));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult removeById(String id) {
        return AjaxResult.success(this.mallUserCartService.deleteById(id));
    }

}

