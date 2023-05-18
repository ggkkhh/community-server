package com.roydon.business.news.service;

import com.roydon.business.news.entity.MallGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (MallGoods)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:04:35
 */
public interface IMallGoodsService {

    /**
     * 通过ID查询单条数据
     *
     * @param goodsId 主键
     * @return 实例对象
     */
    MallGoods queryById(String goodsId);

    /**
     * 分页查询
     *
     * @param mallGoods 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<MallGoods> queryByPage(MallGoods mallGoods, PageRequest pageRequest);

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
