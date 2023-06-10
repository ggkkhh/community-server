package com.roydon.business.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.mall.domain.entity.MallGoods;
import com.roydon.business.mall.domain.dto.MallGoodsDTO;

/**
 * (MallGoods)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:14:18
 */
public interface IMallGoodsService extends IService<MallGoods> {

    /**
     * 通过ID查询单条数据
     *
     * @param goodsId 主键
     * @return 实例对象
     */
    MallGoods getById(String goodsId);

    /**
     * 分页查询
     *
     * @param mallGoodsDTO
     * @return
     */
    IPage<MallGoods> queryPage(MallGoodsDTO mallGoodsDTO);

    /**
     * 新增数据
     *
     * @param mallGoods 实例对象
     * @return 实例对象
     */
    MallGoods insert(MallGoods mallGoods);

    /**
     * 修改数据
     *
     * @param mallGoods 实例对象
     * @return 实例对象
     */
    MallGoods update(MallGoods mallGoods);

    /**
     * 通过主键删除数据
     *
     * @param goodsId 主键
     * @return 是否成功
     */
    boolean deleteById(String goodsId);

}
