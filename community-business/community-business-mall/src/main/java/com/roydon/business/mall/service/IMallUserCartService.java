package com.roydon.business.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.mall.domain.dto.MallUserCartDTO;
import com.roydon.business.mall.domain.entity.MallUserCart;

import java.util.List;

/**
 * (MallUserCart)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:14:32
 */
public interface IMallUserCartService extends IService<MallUserCart> {

    /**
     * 通过用户id查询
     *
     * @param mallUserCartDTO 用户id
     * @return IPage
     */
    IPage<MallUserCart> queryPage(MallUserCartDTO mallUserCartDTO);

    /**
     * 通过goodsIds批量查询用户的购物车数据
     *
     * @param goodsIds
     * @return
     */
    List<MallUserCart> queryListByGoodsId(List<String> goodsIds);

    /**
     * 新增数据
     *
     * @param mallUserCart 实例对象
     * @return 实例对象
     */
    MallUserCart insert(MallUserCart mallUserCart);

    /**
     * 批量删除
     *
     * @param cartIds cartIds
     * @return boolean
     */
    boolean deleteByIds(String[] cartIds);

}
