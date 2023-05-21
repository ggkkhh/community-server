package com.roydon.business.mall.service;

import com.roydon.business.mall.domain.entity.MallOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (MallOrder)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:14:11
 */
public interface IMallOrderService {

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
     * @param mallOrder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<MallOrder> queryByPage(MallOrder mallOrder, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param mallOrder 实例对象
     * @return 实例对象
     */
    MallOrder insert(MallOrder mallOrder);

    /**
     * 修改数据
     *
     * @param mallOrder 实例对象
     * @return 实例对象
     */
    MallOrder update(MallOrder mallOrder);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
    boolean deleteById(String orderId);

}
