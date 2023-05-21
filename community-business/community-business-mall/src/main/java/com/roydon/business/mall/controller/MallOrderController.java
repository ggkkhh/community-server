package com.roydon.business.mall.controller;

import com.roydon.business.mall.domain.entity.MallOrder;
import com.roydon.business.mall.service.IMallOrderService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (MallOrder)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:14:11
 */
@RestController
@RequestMapping("/app/mallOrder")
public class MallOrderController {

    @Resource
    private IMallOrderService mallOrderService;

    /**
     * 分页查询
     *
     * @param mallOrder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public AjaxResult queryByPage(MallOrder mallOrder, PageRequest pageRequest) {
        return AjaxResult.success(this.mallOrderService.queryByPage(mallOrder, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.mallOrderService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param mallOrder 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(MallOrder mallOrder) {
        return AjaxResult.success(this.mallOrderService.insert(mallOrder));
    }

    /**
     * 编辑数据
     *
     * @param mallOrder 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MallOrder mallOrder) {
        return AjaxResult.success(this.mallOrderService.update(mallOrder));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult removeById(String id) {
        return AjaxResult.success(this.mallOrderService.deleteById(id));
    }

}

