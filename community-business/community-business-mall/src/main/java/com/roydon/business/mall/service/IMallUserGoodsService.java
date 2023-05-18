package com.roydon.business.mall.service;

import com.roydon.business.mall.domain.MallUserGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户具有的商品表(MallUserGoods)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:14:38
 */
public interface IMallUserGoodsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallUserGoods queryById(String id);

    /**
     * 分页查询
     *
     * @param mallUserGoods 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<MallUserGoods> queryByPage(MallUserGoods mallUserGoods, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param mallUserGoods 实例对象
     * @return 实例对象
     */
    MallUserGoods insert(MallUserGoods mallUserGoods);

    /**
     * 修改数据
     *
     * @param mallUserGoods 实例对象
     * @return 实例对象
     */
    MallUserGoods update(MallUserGoods mallUserGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
