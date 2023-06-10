package com.roydon.business.mall.service;

import com.roydon.business.mall.domain.entity.MallUserCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (MallUserCart)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:14:32
 */
public interface IMallUserCartService {

    /**
     * 通过ID查询单条数据
     *
     * @param cartId 主键
     * @return 实例对象
     */
    MallUserCart queryById(String cartId);

    /**
     * 分页查询
     *
     * @param mallUserCart 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<MallUserCart> queryByPage(MallUserCart mallUserCart, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param mallUserCart 实例对象
     * @return 实例对象
     */
    MallUserCart insert(MallUserCart mallUserCart);

    /**
     * 修改数据
     *
     * @param mallUserCart 实例对象
     * @return 实例对象
     */
    MallUserCart update(MallUserCart mallUserCart);

    /**
     * 通过主键删除数据
     *
     * @param cartId 主键
     * @return 是否成功
     */
    boolean deleteById(String cartId);

}
