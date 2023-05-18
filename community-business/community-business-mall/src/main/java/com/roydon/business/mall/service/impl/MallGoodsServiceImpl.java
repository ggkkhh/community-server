package com.roydon.business.mall.service.impl;

import com.roydon.business.mall.entity.MallGoods;
import com.roydon.business.mall.mapper.MallGoodsMapper;
import com.roydon.business.mall.service.IMallGoodsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MallGoods)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:04:35
 */
@Service("mallGoodsService")
public class MallGoodsServiceImpl implements IMallGoodsService {
    @Resource
    private MallGoodsMapper mallGoodsMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param goodsId 主键
     * @return 实例对象
     */
    @Override
    public MallGoods queryById(String goodsId) {
        return this.mallGoodsMapper.queryById(goodsId);
    }

    /**
     * 分页查询
     *
     * @param mallGoods 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<MallGoods> queryByPage(MallGoods mallGoods, PageRequest pageRequest) {
        long total = this.mallGoodsMapper.count(mallGoods);
        return new PageImpl<>(this.mallGoodsMapper.queryAllByLimit(mallGoods, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param mallGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MallGoods insert(MallGoods mallGoods) {
        this.mallGoodsMapper.insert(mallGoods);
        return mallGoods;
    }

    /**
     * 修改数据
     *
     * @param mallGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MallGoods update(MallGoods mallGoods) {
        this.mallGoodsMapper.update(mallGoods);
        return this.queryById(mallGoods.getGoodsId());
    }

    /**
     * 通过主键删除数据
     *
     * @param goodsId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String goodsId) {
        return this.mallGoodsMapper.deleteById(goodsId) > 0;
    }
}
