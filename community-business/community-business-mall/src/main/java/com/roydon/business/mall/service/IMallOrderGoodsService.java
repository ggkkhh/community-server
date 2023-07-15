package com.roydon.business.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.mall.domain.entity.MallOrderGoods;
import com.roydon.business.mall.domain.vo.MallOrderGoodsVO;

import java.util.List;

/**
 * (MallOrderGoods)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:13:57
 */
public interface IMallOrderGoodsService extends IService<MallOrderGoods> {

    /**
     * 根据订单id查询所有订单商品
     *
     * @param orderId
     * @return
     */
    List<MallOrderGoods> getOrderGoodsByOrderId(String orderId);

    /**
     * 根据订单id获取一个订单下的所有商品封装VO
     *
     * @param orderId 订单id
     * @return vo
     */
    List<MallOrderGoodsVO> getOneOrderGoodsByOrderId(String orderId);

}
