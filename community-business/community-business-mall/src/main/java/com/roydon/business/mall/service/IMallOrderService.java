package com.roydon.business.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.mall.domain.dto.MallOrderCreateDTO;
import com.roydon.business.mall.domain.dto.MallOrderDTO;
import com.roydon.business.mall.domain.entity.MallOrder;

/**
 * (MallOrder)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:14:11
 */
public interface IMallOrderService extends IService<MallOrder> {

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    MallOrder queryById(String orderId);

    /**
     * 分页查询
     *
     * @param mallOrderDTO 筛选条件
     * @return 查询结果
     */
    IPage<MallOrder> queryPage(MallOrderDTO mallOrderDTO);

    /**
     * 分页查询ByToken
     *
     * @param mallOrderDTO 筛选条件
     * @return 查询结果
     */
    IPage<MallOrder> queryPageByToken(MallOrderDTO mallOrderDTO);

    /**
     * 新增数据
     *
     * @param mallOrder 实例对象
     * @return 实例对象
     */
    MallOrder insert(MallOrder mallOrder);

    /**
     * 创建订单
     *
     * @param mallOrderCreateDTO
     */
    MallOrder createOrder(MallOrderCreateDTO mallOrderCreateDTO);

    /**
     * 修改数据
     *
     * @param mallOrder 实例对象
     * @return 实例对象
     */
    boolean updateOrder(MallOrder mallOrder);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
    boolean deleteById(String orderId);

    /**
     * 批量删除
     *
     * @param orderIds
     * @return
     */
    boolean removeOrderByIds(String[] orderIds);

    boolean cancelOrder(String orderId);

}
