package com.roydon.business.mall.controller;

import com.roydon.business.mall.domain.entity.MallUserGoods;
import com.roydon.business.mall.service.IMallUserGoodsService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户具有的商品表(MallUserGoods)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:14:38
 */
@RestController
@RequestMapping("/app/mallUserGoods")
public class MallUserGoodsController {

    @Resource
    private IMallUserGoodsService mallUserGoodsService;

    /**
     * 分页查询
     *
     * @param mallUserGoods 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public AjaxResult queryByPage(MallUserGoods mallUserGoods, PageRequest pageRequest) {
        return AjaxResult.success(this.mallUserGoodsService.queryByPage(mallUserGoods, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.mallUserGoodsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param mallUserGoods 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(MallUserGoods mallUserGoods) {
        return AjaxResult.success(this.mallUserGoodsService.insert(mallUserGoods));
    }

    /**
     * 编辑数据
     *
     * @param mallUserGoods 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MallUserGoods mallUserGoods) {
        return AjaxResult.success(this.mallUserGoodsService.update(mallUserGoods));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult removeById(String id) {
        return AjaxResult.success(this.mallUserGoodsService.deleteById(id));
    }

}

