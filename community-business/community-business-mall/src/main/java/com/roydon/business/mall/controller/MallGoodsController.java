package com.roydon.business.mall.controller;

import com.roydon.business.mall.entity.MallGoods;
import com.roydon.business.mall.service.IMallGoodsService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (MallGoods)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:04:33
 */
@RestController
@RequestMapping("/app/mallGoods")
public class MallGoodsController {

    @Resource
    private IMallGoodsService mallGoodsService;

    /**
     * 分页查询
     *
     * @param mallGoods 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public AjaxResult queryByPage(MallGoods mallGoods, PageRequest pageRequest) {
        return AjaxResult.success(this.mallGoodsService.queryByPage(mallGoods, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.mallGoodsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param mallGoods 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(MallGoods mallGoods) {
        return AjaxResult.success(this.mallGoodsService.insert(mallGoods));
    }

    /**
     * 编辑数据
     *
     * @param mallGoods 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MallGoods mallGoods) {
        return AjaxResult.success(this.mallGoodsService.update(mallGoods));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult removeById(String id) {
        return AjaxResult.success(this.mallGoodsService.deleteById(id));
    }

}

