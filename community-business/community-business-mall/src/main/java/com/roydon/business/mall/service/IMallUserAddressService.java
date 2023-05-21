package com.roydon.business.mall.service;

import com.roydon.business.mall.domain.entity.MallUserAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (MallUserAddress)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:14:25
 */
public interface IMallUserAddressService {

    /**
     * 通过ID查询单条数据
     *
     * @param addressId 主键
     * @return 实例对象
     */
    MallUserAddress queryById(String addressId);

    /**
     * 分页查询
     *
     * @param mallUserAddress 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<MallUserAddress> queryByPage(MallUserAddress mallUserAddress, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param mallUserAddress 实例对象
     * @return 实例对象
     */
    MallUserAddress insert(MallUserAddress mallUserAddress);

    /**
     * 修改数据
     *
     * @param mallUserAddress 实例对象
     * @return 实例对象
     */
    MallUserAddress update(MallUserAddress mallUserAddress);

    /**
     * 通过主键删除数据
     *
     * @param addressId 主键
     * @return 是否成功
     */
    boolean deleteById(String addressId);

}
