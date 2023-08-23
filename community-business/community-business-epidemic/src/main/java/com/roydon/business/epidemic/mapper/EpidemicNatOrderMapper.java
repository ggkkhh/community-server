package com.roydon.business.epidemic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.epidemic.domain.entity.EpidemicNatOrder;

import java.util.List;

/**
 * 预约核酸检测NATMapper接口
 *
 * @author roydon
 * @date 2023-08-23
 */
public interface EpidemicNatOrderMapper extends BaseMapper<EpidemicNatOrder> {

    /**
     * 查询预约核酸检测NAT
     *
     * @param orderId 预约核酸检测NAT主键
     * @return 预约核酸检测NAT
     */
    EpidemicNatOrder selectEpidemicNatOrderByOrderId(Long orderId);

    /**
     * 查询预约核酸检测NAT列表
     *
     * @param epidemicNatOrder 预约核酸检测NAT
     * @return 预约核酸检测NAT集合
     */
    List<EpidemicNatOrder> selectEpidemicNatOrderList(EpidemicNatOrder epidemicNatOrder);

    /**
     * 新增预约核酸检测NAT
     *
     * @param epidemicNatOrder 预约核酸检测NAT
     * @return 结果
     */
    int insertEpidemicNatOrder(EpidemicNatOrder epidemicNatOrder);

    /**
     * 修改预约核酸检测NAT
     *
     * @param epidemicNatOrder 预约核酸检测NAT
     * @return 结果
     */
    int updateEpidemicNatOrder(EpidemicNatOrder epidemicNatOrder);

    /**
     * 删除预约核酸检测NAT
     *
     * @param orderId 预约核酸检测NAT主键
     * @return 结果
     */
    int deleteEpidemicNatOrderByOrderId(Long orderId);

    /**
     * 批量删除预约核酸检测NAT
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteEpidemicNatOrderByOrderIds(Long[] orderIds);
}
