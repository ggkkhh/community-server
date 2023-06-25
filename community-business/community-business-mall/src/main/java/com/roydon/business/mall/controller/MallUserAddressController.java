package com.roydon.business.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.mall.domain.dto.MallUserAddressDTO;
import com.roydon.business.mall.domain.entity.MallUserAddress;
import com.roydon.business.mall.service.IMallUserAddressService;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.utils.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
     * @param MallUserAddressDTO
     * @return
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody MallUserAddressDTO MallUserAddressDTO) {
        IPage<MallUserAddress> addressIPage = mallUserAddressService.queryPage(MallUserAddressDTO);
        List<MallUserAddress> records = addressIPage.getRecords();
        return AjaxResult.genTableData(records, addressIPage.getTotal());
    }

    /**
     * 新增数据
     *
     * @param mallUserAddress 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MallUserAddress mallUserAddress) {
        return AjaxResult.success(mallUserAddressService.insert(mallUserAddress));
    }

    /**
     * 编辑数据
     *
     * @param mallUserAddress 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MallUserAddress mallUserAddress) {
        return AjaxResult.success(mallUserAddressService.updateById(mallUserAddress));
    }

    /**
     * 删除数据
     *
     * @param addressIds 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{addressIds}")
    public AjaxResult removeById(@PathVariable String[] addressIds) {
        return AjaxResult.success(mallUserAddressService.deleteByIds(addressIds));
    }

    /**
     * 获取默认收货地址
     */
    @GetMapping("/default")
    public AjaxResult getOneDefault() {
        LambdaQueryWrapper<MallUserAddress> queryWrapper = new LambdaQueryWrapper<>();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        queryWrapper.eq(MallUserAddress::getUserId, loginUser.getUserId()).eq(MallUserAddress::getIsDefault, "1");
        return AjaxResult.success(mallUserAddressService.getOne(queryWrapper));
    }

}

