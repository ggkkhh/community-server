package com.roydon.business.epidemic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.epidemic.domain.entity.EpidemicIsolationPolicy;

import java.util.List;

/**
 * 隔离政策Service接口
 *
 * @author roydon
 * @date 2023-08-21
 */
public interface IEpidemicIsolationPolicyService extends IService<EpidemicIsolationPolicy> {
    /**
     * 查询隔离政策
     *
     * @param policyId 隔离政策主键
     * @return 隔离政策
     */
    EpidemicIsolationPolicy selectEpidemicIsolationPolicyByPolicyId(Long policyId);

    /**
     * 查询隔离政策列表
     *
     * @param epidemicIsolationPolicy 隔离政策
     * @return 隔离政策集合
     */
    List<EpidemicIsolationPolicy> selectEpidemicIsolationPolicyList(EpidemicIsolationPolicy epidemicIsolationPolicy);

    /**
     * 新增隔离政策
     *
     * @param epidemicIsolationPolicy 隔离政策
     * @return 结果
     */
    int insertEpidemicIsolationPolicy(EpidemicIsolationPolicy epidemicIsolationPolicy);

    /**
     * 修改隔离政策
     *
     * @param epidemicIsolationPolicy 隔离政策
     * @return 结果
     */
    int updateEpidemicIsolationPolicy(EpidemicIsolationPolicy epidemicIsolationPolicy);

    /**
     * 批量删除隔离政策
     *
     * @param policyIds 需要删除的隔离政策主键集合
     * @return 结果
     */
    int deleteEpidemicIsolationPolicyByPolicyIds(Long[] policyIds);

    /**
     * 删除隔离政策信息
     *
     * @param policyId 隔离政策主键
     * @return 结果
     */
    int deleteEpidemicIsolationPolicyByPolicyId(Long policyId);
}
