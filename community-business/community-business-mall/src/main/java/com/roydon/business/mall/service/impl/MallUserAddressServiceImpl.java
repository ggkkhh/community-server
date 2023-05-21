package com.roydon.business.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.mall.domain.entity.MallUserAddress;
import com.roydon.business.mall.mapper.MallUserAddressMapper;
import com.roydon.business.mall.service.IMallUserAddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MallUserAddress)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:14:25
 */
@Service("mallUserAddressService")
public class MallUserAddressServiceImpl extends ServiceImpl<MallUserAddressMapper, MallUserAddress> implements IMallUserAddressService {
    @Resource
    private MallUserAddressMapper mallUserAddressMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param addressId 主键
     * @return 实例对象
     */
    @Override
    public MallUserAddress queryById(String addressId) {
        return this.mallUserAddressMapper.queryById(addressId);
    }

    /**
     * 分页查询
     *
     * @param mallUserAddress 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<MallUserAddress> queryByPage(MallUserAddress mallUserAddress, PageRequest pageRequest) {
        long total = this.mallUserAddressMapper.count(mallUserAddress);
        return new PageImpl<>(this.mallUserAddressMapper.queryAllByLimit(mallUserAddress, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param mallUserAddress 实例对象
     * @return 实例对象
     */
    @Override
    public MallUserAddress insert(MallUserAddress mallUserAddress) {
        this.mallUserAddressMapper.insert(mallUserAddress);
        return mallUserAddress;
    }

    /**
     * 修改数据
     *
     * @param mallUserAddress 实例对象
     * @return 实例对象
     */
    @Override
    public MallUserAddress update(MallUserAddress mallUserAddress) {
        this.mallUserAddressMapper.update(mallUserAddress);
        return this.queryById(mallUserAddress.getAddressId());
    }

    /**
     * 通过主键删除数据
     *
     * @param addressId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String addressId) {
        return this.mallUserAddressMapper.deleteById(addressId) > 0;
    }
}
