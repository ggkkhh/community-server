package com.roydon.business.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.mall.domain.dto.MallOrderDTO;
import com.roydon.business.mall.domain.entity.MallOrder;
import com.roydon.business.mall.mapper.MallOrderMapper;
import com.roydon.business.mall.service.IMallOrderService;
import com.roydon.common.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MallOrder)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:14:11
 */
@Service("mallOrderService")
public class MallOrderServiceImpl extends ServiceImpl<MallOrderMapper, MallOrder> implements IMallOrderService {
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
     * @param mallOrderDTO 筛选条件：1.用户账号2.支付状态
     * @return 查询结果
     */
    @Override
    public IPage<MallOrder> queryPage(MallOrderDTO mallOrderDTO) {
        LambdaQueryWrapper<MallOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotEmpty(mallOrderDTO.getUserName()), MallOrder::getUserName, mallOrderDTO.getUserName());
        queryWrapper.eq(StringUtil.isNotEmpty(mallOrderDTO.getPayStatus()), MallOrder::getPayStatus, mallOrderDTO.getPayStatus());
        return page(new Page<>(mallOrderDTO.getPageNum(), mallOrderDTO.getPageSize()), queryWrapper);
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
