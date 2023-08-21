package com.roydon.business.epidemic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.epidemic.domain.entity.EpidemicIsolationPolicy;
import com.roydon.business.epidemic.mapper.EpidemicIsolationPolicyMapper;
import com.roydon.business.epidemic.service.IEpidemicIsolationPolicyService;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 隔离政策Service业务层处理
 *
 * @author roydon
 * @date 2023-08-21
 */
@Service
public class EpidemicIsolationPolicyServiceImpl extends ServiceImpl<EpidemicIsolationPolicyMapper, EpidemicIsolationPolicy> implements IEpidemicIsolationPolicyService {

    @Resource
    private EpidemicIsolationPolicyMapper epidemicIsolationPolicyMapper;

    /**
     * 查询隔离政策
     *
     * @param policyId 隔离政策主键
     * @return 隔离政策
     */
    @Override
    public EpidemicIsolationPolicy selectEpidemicIsolationPolicyByPolicyId(Long policyId) {
        return epidemicIsolationPolicyMapper.selectEpidemicIsolationPolicyByPolicyId(policyId);
    }

    /**
     * 查询隔离政策列表
     *
     * @param epidemicIsolationPolicy 隔离政策
     * @return 隔离政策
     */
    @Override
    public List<EpidemicIsolationPolicy> selectEpidemicIsolationPolicyList(EpidemicIsolationPolicy epidemicIsolationPolicy) {
        return epidemicIsolationPolicyMapper.selectEpidemicIsolationPolicyList(epidemicIsolationPolicy);
    }

    /**
     * 新增隔离政策
     *
     * @param epidemicIsolationPolicy 隔离政策
     * @return 结果
     */
    @Override
    public int insertEpidemicIsolationPolicy(EpidemicIsolationPolicy epidemicIsolationPolicy) {
        epidemicIsolationPolicy.setCreateTime(DateUtils.getNowDate());
        epidemicIsolationPolicy.setCreateBy(SecurityUtils.getUsername());
        return epidemicIsolationPolicyMapper.insertEpidemicIsolationPolicy(epidemicIsolationPolicy);
    }

    /**
     * 修改隔离政策
     *
     * @param epidemicIsolationPolicy 隔离政策
     * @return 结果
     */
    @Override
    public int updateEpidemicIsolationPolicy(EpidemicIsolationPolicy epidemicIsolationPolicy) {
        epidemicIsolationPolicy.setUpdateTime(DateUtils.getNowDate());
        epidemicIsolationPolicy.setUpdateBy(SecurityUtils.getUsername());
        return epidemicIsolationPolicyMapper.updateEpidemicIsolationPolicy(epidemicIsolationPolicy);
    }

    /**
     * 批量删除隔离政策
     *
     * @param policyIds 需要删除的隔离政策主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicIsolationPolicyByPolicyIds(Long[] policyIds) {
        return epidemicIsolationPolicyMapper.deleteEpidemicIsolationPolicyByPolicyIds(policyIds);
    }

    /**
     * 删除隔离政策信息
     *
     * @param policyId 隔离政策主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicIsolationPolicyByPolicyId(Long policyId) {
        return epidemicIsolationPolicyMapper.deleteEpidemicIsolationPolicyByPolicyId(policyId);
    }
}
