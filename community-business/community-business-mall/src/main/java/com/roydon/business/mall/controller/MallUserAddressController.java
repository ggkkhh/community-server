package com.roydon.business.mall.controller;

import com.roydon.business.mall.domain.entity.MallUserAddress;
import com.roydon.business.mall.service.IMallUserAddressService;
import com.roydon.common.core.domain.AjaxResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (MallUserAddress)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:14:25
 */
@RestController
@RequestMapping("/app/mallUserAddress")
public class MallUserAddressController {

    @Resource
    private IMallUserAddressService mallUserAddressService;

    /**
     * 分页查询
     *
     * @param mallUserAddress 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public AjaxResult queryByPage(MallUserAddress mallUserAddress, PageRequest pageRequest) {
        return AjaxResult.success(this.mallUserAddressService.queryByPage(mallUserAddress, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.mallUserAddressService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param mallUserAddress 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(MallUserAddress mallUserAddress) {
        return AjaxResult.success(this.mallUserAddressService.insert(mallUserAddress));
    }

    /**
     * 编辑数据
     *
     * @param mallUserAddress 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MallUserAddress mallUserAddress) {
        return AjaxResult.success(this.mallUserAddressService.update(mallUserAddress));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult removeById(String id) {
        return AjaxResult.success(this.mallUserAddressService.deleteById(id));
    }

}

