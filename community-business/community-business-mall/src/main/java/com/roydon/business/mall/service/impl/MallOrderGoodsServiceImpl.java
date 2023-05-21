package com.roydon.business.mall.service.impl;

import com.roydon.business.mall.domain.entity.MallOrderGoods;
import com.roydon.business.mall.mapper.MallOrderGoodsMapper;
import com.roydon.business.mall.service.IMallOrderGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (MallOrderGoods)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:13:57
 */
@Service("mallOrderGoodsService")
public class MallOrderGoodsServiceImpl implements IMallOrderGoodsService {
    @Resource
    private MallOrderGoodsMapper mallOrderGoodsMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MallOrderGoods queryById(String id) {
        return this.mallOrderGoodsMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mallOrderGoods 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<MallOrderGoods> queryByPage(MallOrderGoods mallOrderGoods, PageRequest pageRequest) {
        long total = this.mallOrderGoodsMapper.count(mallOrderGoods);
        return new PageImpl<>(this.mallOrderGoodsMapper.queryAllByLimit(mallOrderGoods, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param mallOrderGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MallOrderGoods insert(MallOrderGoods mallOrderGoods) {
        this.mallOrderGoodsMapper.insert(mallOrderGoods);
        return mallOrderGoods;
    }

    /**
     * 修改数据
     *
     * @param mallOrderGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MallOrderGoods update(MallOrderGoods mallOrderGoods) {
        this.mallOrderGoodsMapper.update(mallOrderGoods);
        return this.queryById(mallOrderGoods.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.mallOrderGoodsMapper.deleteById(id) > 0;
    }
}
