package com.roydon.business.mall.service.impl;

import com.roydon.business.mall.domain.MallUserCart;
import com.roydon.business.mall.mapper.MallUserCartMapper;
import com.roydon.business.mall.service.IMallUserCartService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (MallUserCart)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:14:32
 */
@Service("mallUserCartService")
public class MallUserCartServiceImpl implements IMallUserCartService {
    @Resource
    private MallUserCartMapper mallUserCartMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param cartId 主键
     * @return 实例对象
     */
    @Override
    public MallUserCart queryById(String cartId) {
        return this.mallUserCartMapper.queryById(cartId);
    }

    /**
     * 分页查询
     *
     * @param mallUserCart 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<MallUserCart> queryByPage(MallUserCart mallUserCart, PageRequest pageRequest) {
        long total = this.mallUserCartMapper.count(mallUserCart);
        return new PageImpl<>(this.mallUserCartMapper.queryAllByLimit(mallUserCart, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param mallUserCart 实例对象
     * @return 实例对象
     */
    @Override
    public MallUserCart insert(MallUserCart mallUserCart) {
        this.mallUserCartMapper.insert(mallUserCart);
        return mallUserCart;
    }

    /**
     * 修改数据
     *
     * @param mallUserCart 实例对象
     * @return 实例对象
     */
    @Override
    public MallUserCart update(MallUserCart mallUserCart) {
        this.mallUserCartMapper.update(mallUserCart);
        return this.queryById(mallUserCart.getCartId());
    }

    /**
     * 通过主键删除数据
     *
     * @param cartId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String cartId) {
        return this.mallUserCartMapper.deleteById(cartId) > 0;
    }
}
