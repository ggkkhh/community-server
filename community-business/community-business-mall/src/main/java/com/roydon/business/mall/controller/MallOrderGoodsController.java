package com.roydon.business.mall.controller;

import com.roydon.business.mall.domain.entity.MallOrderGoods;
import com.roydon.business.mall.service.IMallOrderGoodsService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (MallOrderGoods)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:13:55
 */
@RestController
@RequestMapping("/app/mallOrderGoods")
public class MallOrderGoodsController {

    @Resource
    private IMallOrderGoodsService mallOrderGoodsService;

    /**
     * 分页查询
     *
     * @param mallOrderGoods 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public AjaxResult queryByPage(MallOrderGoods mallOrderGoods, PageRequest pageRequest) {
        return AjaxResult.success(this.mallOrderGoodsService.queryByPage(mallOrderGoods, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.mallOrderGoodsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param mallOrderGoods 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(MallOrderGoods mallOrderGoods) {
        return AjaxResult.success(this.mallOrderGoodsService.insert(mallOrderGoods));
    }

    /**
     * 编辑数据
     *
     * @param mallOrderGoods 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MallOrderGoods mallOrderGoods) {
        return AjaxResult.success(this.mallOrderGoodsService.update(mallOrderGoods));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult removeById(String id) {
        return AjaxResult.success(this.mallOrderGoodsService.deleteById(id));
    }

}

