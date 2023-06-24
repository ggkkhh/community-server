package com.roydon.business.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.mall.domain.dto.MallUserCartDTO;
import com.roydon.business.mall.domain.entity.MallUserCart;

/**
 * (MallUserCart)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:14:32
 */
public interface IMallUserCartService {

    /**
     * 通过用户id查询
     *
     * @param mallUserCartDTO 用户id
     * @return IPage
     */
    IPage<MallUserCart> queryPage(MallUserCartDTO mallUserCartDTO);

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
