package com.roydon.business.mall.service.impl;

import com.roydon.business.mall.domain.entity.MallUserGoods;
import com.roydon.business.mall.mapper.MallUserGoodsMapper;
import com.roydon.business.mall.service.IMallUserGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 用户具有的商品表(MallUserGoods)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:14:38
 */
@Service("mallUserGoodsService")
public class MallUserGoodsServiceImpl implements IMallUserGoodsService {
    @Resource
    private MallUserGoodsMapper mallUserGoodsMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MallUserGoods queryById(String id) {
        return this.mallUserGoodsMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mallUserGoods 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<MallUserGoods> queryByPage(MallUserGoods mallUserGoods, PageRequest pageRequest) {
        long total = this.mallUserGoodsMapper.count(mallUserGoods);
        return new PageImpl<>(this.mallUserGoodsMapper.queryAllByLimit(mallUserGoods, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param mallUserGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MallUserGoods insert(MallUserGoods mallUserGoods) {
        this.mallUserGoodsMapper.insert(mallUserGoods);
        return mallUserGoods;
    }

    /**
     * 修改数据
     *
     * @param mallUserGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MallUserGoods update(MallUserGoods mallUserGoods) {
        this.mallUserGoodsMapper.update(mallUserGoods);
        return this.queryById(mallUserGoods.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.mallUserGoodsMapper.deleteById(id) > 0;
    }
}
