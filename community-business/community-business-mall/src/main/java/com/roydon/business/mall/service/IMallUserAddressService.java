package com.roydon.business.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.mall.domain.dto.MallUserAddressDTO;
import com.roydon.business.mall.domain.entity.MallUserAddress;

/**
 * (MallUserAddress)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:14:25
 */
public interface IMallUserAddressService extends IService<MallUserAddress> {

    /**
     * 通过用户id查询收货地址
     *
     * @param MallUserAddressDTO 用户id
     * @return IPage
     */
    IPage<MallUserAddress> queryPage(MallUserAddressDTO MallUserAddressDTO);

    /**
     * 新增数据
     *
     * @param mallUserAddress 实例对象
     * @return 实例对象
     */
    MallUserAddress insert(MallUserAddress mallUserAddress);

    /**
     * 批量删除
     *
     * @param addressIds addressIds
     * @return boolean
     */
    boolean deleteByIds(String[] addressIds);

}
