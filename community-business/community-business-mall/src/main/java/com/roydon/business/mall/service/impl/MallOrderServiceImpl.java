package com.roydon.business.mall.service.impl;

import com.roydon.business.mall.domain.MallOrder;
import com.roydon.business.mall.mapper.MallOrderMapper;
import com.roydon.business.mall.service.IMallOrderService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (MallOrder)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:14:11
 */
@Service("mallOrderService")
public class MallOrderServiceImpl implements IMallOrderService {
    @Resource
    private MallOrderMapper mallOrderMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    @Override
    public MallOrder queryById(String orderId) {
        return this.mallOrderMapper.queryById(orderId);
    }

    /**
     * 分页查询
     *
     * @param mallOrder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<MallOrder> queryByPage(MallOrder mallOrder, PageRequest pageRequest) {
        long total = this.mallOrderMapper.count(mallOrder);
        return new PageImpl<>(this.mallOrderMapper.queryAllByLimit(mallOrder, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param mallOrder 实例对象
     * @return 实例对象
     */
    @Override
    public MallOrder insert(MallOrder mallOrder) {
        this.mallOrderMapper.insert(mallOrder);
        return mallOrder;
    }

    /**
     * 修改数据
     *
     * @param mallOrder 实例对象
     * @return 实例对象
     */
    @Override
    public MallOrder update(MallOrder mallOrder) {
        this.mallOrderMapper.update(mallOrder);
        return this.queryById(mallOrder.getOrderId());
    }

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String orderId) {
        return this.mallOrderMapper.deleteById(orderId) > 0;
    }
}
