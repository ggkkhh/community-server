package com.roydon.business.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.mall.domain.dto.MallUserAddressDTO;
import com.roydon.business.mall.domain.entity.MallUserAddress;
import com.roydon.business.mall.mapper.MallUserAddressMapper;
import com.roydon.business.mall.service.IMallUserAddressService;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.enums.AddressTypeEnum;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.mall.AddressUtils;
import com.roydon.common.utils.uniqueid.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 新增收货地址
     * TODO 若起始无收货地址，新增的一条强制为默认收货地址
     *
     * @param mallUserAddress 实例对象
     *                        {
     *                        "nickname": "guoyicheng",
     *                        "telephone": "18203707837",
     *                        "regionCode": "411481",
     *                        "completeAddress": "嗯~ o(*￣▽￣*)o",
     *                        "isDefault": "0"
     *                        }
     * @return MallUserAddress
     */
    @Transactional
    @Override
    public MallUserAddress insert(MallUserAddress mallUserAddress) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        mallUserAddress.setAddressId(IdGenerator.generatorShortId());
        mallUserAddress.setUserId(loginUser.getUserId());
        mallUserAddress.setCommunityId(loginUser.getDeptId());
        mallUserAddress.setCreateTime(new Date());
        mallUserAddress.setCreateBy(loginUser.getUser().getUserName());
        // 填充省市区编码
        String regionCode = mallUserAddress.getRegionCode();
        String provinceCode, cityCode;
        try {
            provinceCode = AddressUtils.getProvinceCode(regionCode, AddressTypeEnum.PROVINCE);
            cityCode = AddressUtils.getProvinceCode(regionCode, AddressTypeEnum.CITY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mallUserAddress.setProvinceCode(provinceCode);
        mallUserAddress.setCityCode(cityCode);
        // 判断是否为默认地址
        if (mallUserAddress.getIsDefault().equals("1")) {
            // 用户勾选了为默认地址，其他是默认地址的设为非默认
            LambdaUpdateWrapper<MallUserAddress> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(MallUserAddress::getUserId, loginUser.getUserId()).set(MallUserAddress::getIsDefault, "0");
            update(updateWrapper);
        }
        saveOrUpdate(mallUserAddress);
        return mallUserAddress;
    }

    /**
     * 逻辑删除
     *
     * @param addressIds addressIds
     * @return boolean
     */
    @Override
    public boolean deleteByIds(String[] addressIds) {
//        LambdaUpdateWrapper<MallUserAddress> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.eq(MallUserAddress::getUserId, loginUser.getUserId()).set(MallUserAddress::getIsDefault, "0");
//        update(updateWrapper);
        return removeBatchByIds(Arrays.asList(addressIds));
    }
}
