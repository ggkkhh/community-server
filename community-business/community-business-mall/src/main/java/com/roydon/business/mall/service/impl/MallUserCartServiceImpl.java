package com.roydon.business.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.mall.domain.dto.MallUserCartDTO;
import com.roydon.business.mall.domain.entity.MallUserCart;
import com.roydon.business.mall.mapper.MallUserCartMapper;
import com.roydon.business.mall.service.IMallUserCartService;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.uniqueid.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

/**
 * (MallUserCart)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:14:32
 */
@Service("mallUserCartService")
public class MallUserCartServiceImpl extends ServiceImpl<MallUserCartMapper, MallUserCart> implements IMallUserCartService {

    @Resource
    private MallUserCartMapper mallUserCartMapper;

    @Override
    public IPage<MallUserCart> queryPage(MallUserCartDTO mallUserCartDTO) {
        LambdaQueryWrapper<MallUserCart> queryWrapper = new LambdaQueryWrapper<>();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        queryWrapper.eq(MallUserCart::getUserId, loginUser.getUserId());
        queryWrapper.orderByDesc(MallUserCart::getCreateTime);
        return this.page(new Page<>(mallUserCartDTO.getPageNum(), mallUserCartDTO.getPageSize()), queryWrapper);
    }

    /**
     * 新增数据
     *
     * @param mallUserCart 实例对象
     * @return 实例对象
     */
    @Override
    public MallUserCart insert(MallUserCart mallUserCart) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        mallUserCart.setCartId(IdGenerator.generatorId());
        mallUserCart.setUserId(loginUser.getUserId());
        // 存在此商品直接返回
        LambdaQueryWrapper<MallUserCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MallUserCart::getGoodsId, mallUserCart.getGoodsId());
        MallUserCart one = getOne(queryWrapper);
        if (StringUtil.isNotEmpty(one)) {
            return one;
        }
        mallUserCart.setGoodsId(mallUserCart.getGoodsId());
        mallUserCart.setGoodsCount(1);
        // 默认此商品未选中
        mallUserCart.setDefaultActive("0");
        mallUserCart.setCreateTime(new Date());
        // 有数据即更新
        saveOrUpdate(mallUserCart);
        return mallUserCart;
    }

    /**
     * 通过主键删除数据
     *
     * @param cartIds 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteByIds(String[] cartIds) {
        return removeBatchByIds(Arrays.asList(cartIds));
    }

}
