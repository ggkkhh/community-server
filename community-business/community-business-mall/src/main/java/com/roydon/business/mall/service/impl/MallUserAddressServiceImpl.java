package com.roydon.business.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.mall.domain.dto.MallUserAddressDTO;
import com.roydon.business.mall.domain.entity.MallUserAddress;
import com.roydon.business.mall.mapper.MallUserAddressMapper;
import com.roydon.business.mall.service.IMallUserAddressService;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.uniqueid.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

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

    @Override
    public IPage<MallUserAddress> queryPage(MallUserAddressDTO mallUserAddressDTO) {
        LambdaQueryWrapper<MallUserAddress> queryWrapper = new LambdaQueryWrapper<>();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        queryWrapper.eq(MallUserAddress::getUserId, loginUser.getUserId());
        queryWrapper.orderByDesc(MallUserAddress::getCreateTime, MallUserAddress::getIsDefault);
        return this.page(new Page<>(mallUserAddressDTO.getPageNum(), mallUserAddressDTO.getPageSize()), queryWrapper);
    }

    @Override
    public MallUserAddress insert(MallUserAddress mallUserAddress) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        mallUserAddress.setAddressId(IdGenerator.generatorId());
        mallUserAddress.setUserId(loginUser.getUserId());
        mallUserAddress.setCommunityId(loginUser.getDeptId());
        mallUserAddress.setCreateTime(new Date());
        saveOrUpdate(mallUserAddress);
        return mallUserAddress;
    }

    @Override
    public boolean deleteByIds(String[] addressIds) {
        return removeBatchByIds(Arrays.asList(addressIds));
    }
}
