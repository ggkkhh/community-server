package com.roydon.business.epidemic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.epidemic.domain.dto.EpidemicNatOrderPageDTO;
import com.roydon.business.epidemic.domain.entity.EpidemicNatOrder;
import com.roydon.business.epidemic.enums.natOrderStatusEnum;
import com.roydon.business.epidemic.mapper.EpidemicNatOrderMapper;
import com.roydon.business.epidemic.service.IEpidemicNatOrderService;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预约核酸检测NATService业务层处理
 *
 * @author roydon
 * @date 2023-08-23
 */
@Service
public class EpidemicNatOrderServiceImpl extends ServiceImpl<EpidemicNatOrderMapper, EpidemicNatOrder> implements IEpidemicNatOrderService {

    @Resource
    private EpidemicNatOrderMapper epidemicNatOrderMapper;

    @Resource
    private ISysUserService sysUserService;

    /**
     * 分页查询我的预约记录
     *
     * @param pageDTO
     * @return
     */
    @Override
    public IPage<EpidemicNatOrder> queryPageForMine(EpidemicNatOrderPageDTO pageDTO) {
        String username = SecurityUtils.getUsername();
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        LambdaQueryWrapper<EpidemicNatOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EpidemicNatOrder::getTelephone, sysUser.getPhonenumber());
        queryWrapper.eq(EpidemicNatOrder::getIdCard, sysUser.getIdCard());
        queryWrapper.orderByDesc(EpidemicNatOrder::getOrderTime);
        return page(new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize()), queryWrapper);
    }

    /**
     * 查询预约核酸检测NAT
     *
     * @param orderId 预约核酸检测NAT主键
     * @return 预约核酸检测NAT
     */
    @Override
    public EpidemicNatOrder selectEpidemicNatOrderByOrderId(Long orderId) {
        return epidemicNatOrderMapper.selectEpidemicNatOrderByOrderId(orderId);
    }

    /**
     * 查询预约核酸检测NAT列表
     *
     * @param epidemicNatOrder 预约核酸检测NAT
     * @return 预约核酸检测NAT
     */
    @Override
    public List<EpidemicNatOrder> selectEpidemicNatOrderList(EpidemicNatOrder epidemicNatOrder) {
        return epidemicNatOrderMapper.selectEpidemicNatOrderList(epidemicNatOrder);
    }

    /**
     * 新增预约核酸检测NAT
     *
     * @param epidemicNatOrder 预约核酸检测NAT
     * @return 结果
     */
    @Override
    public int insertEpidemicNatOrder(EpidemicNatOrder epidemicNatOrder) {
        epidemicNatOrder.setCreateTime(DateUtils.getNowDate());
        epidemicNatOrder.setOrderTime(DateUtils.getNowDate());
        epidemicNatOrder.setOrderStatus(natOrderStatusEnum.BEGIN.getCode());
        return epidemicNatOrderMapper.insertEpidemicNatOrder(epidemicNatOrder);
    }

    /**
     * 修改预约核酸检测NAT
     *
     * @param epidemicNatOrder 预约核酸检测NAT
     * @return 结果
     */
    @Override
    public int updateEpidemicNatOrder(EpidemicNatOrder epidemicNatOrder) {
        epidemicNatOrder.setUpdateTime(DateUtils.getNowDate());
        return epidemicNatOrderMapper.updateEpidemicNatOrder(epidemicNatOrder);
    }

    /**
     * 批量删除预约核酸检测NAT
     *
     * @param orderIds 需要删除的预约核酸检测NAT主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicNatOrderByOrderIds(Long[] orderIds) {
        return epidemicNatOrderMapper.deleteEpidemicNatOrderByOrderIds(orderIds);
    }

    /**
     * 删除预约核酸检测NAT信息
     *
     * @param orderId 预约核酸检测NAT主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicNatOrderByOrderId(Long orderId) {
        return epidemicNatOrderMapper.deleteEpidemicNatOrderByOrderId(orderId);
    }
}
