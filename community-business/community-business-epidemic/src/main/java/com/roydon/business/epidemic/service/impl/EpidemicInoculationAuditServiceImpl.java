package com.roydon.business.epidemic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.epidemic.domain.entity.EpidemicInoculationAudit;
import com.roydon.business.epidemic.enums.AuditStatusEnum;
import com.roydon.business.epidemic.mapper.EpidemicInoculationAuditMapper;
import com.roydon.business.epidemic.service.IEpidemicInoculationAuditService;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 疫苗接种审核Service业务层处理
 *
 * @author roydon
 * @date 2023-08-10
 */
@Service
public class EpidemicInoculationAuditServiceImpl extends ServiceImpl<EpidemicInoculationAuditMapper, EpidemicInoculationAudit> implements IEpidemicInoculationAuditService {

    @Resource
    private EpidemicInoculationAuditMapper epidemicInoculationAuditMapper;

    /**
     * 查询疫苗接种审核
     *
     * @param auditId 疫苗接种审核主键
     * @return 疫苗接种审核
     */
    @Override
    public EpidemicInoculationAudit selectEpidemicInoculationAuditByAuditId(Long auditId) {
        return epidemicInoculationAuditMapper.selectEpidemicInoculationAuditByAuditId(auditId);
    }

    /**
     * 查询疫苗接种审核列表
     *
     * @param epidemicInoculationAudit 疫苗接种审核
     * @return 疫苗接种审核
     */
    @Override
    public List<EpidemicInoculationAudit> selectEpidemicInoculationAuditList(EpidemicInoculationAudit epidemicInoculationAudit) {
        return epidemicInoculationAuditMapper.selectEpidemicInoculationAuditList(epidemicInoculationAudit);
    }

    /**
     * 新增疫苗接种审核
     *
     * @param epidemicInoculationAudit 疫苗接种审核
     * @return 结果
     */
    @Override
    public int insertEpidemicInoculationAudit(EpidemicInoculationAudit epidemicInoculationAudit) {
        epidemicInoculationAudit.setCreateTime(DateUtils.getNowDate());
        epidemicInoculationAudit.setCreateBy(SecurityUtils.getUsername());
        epidemicInoculationAudit.setUserId(SecurityUtils.getUserId());
        epidemicInoculationAudit.setUsername(SecurityUtils.getUsername());
        return epidemicInoculationAuditMapper.insertEpidemicInoculationAudit(epidemicInoculationAudit);
    }

    /**
     * 修改疫苗接种审核
     *
     * @param epidemicInoculationAudit 疫苗接种审核
     * @return 结果
     */
    @Transactional
    @Override
    public int updateEpidemicInoculationAudit(EpidemicInoculationAudit epidemicInoculationAudit) {
        epidemicInoculationAudit.setUpdateTime(DateUtils.getNowDate());
        epidemicInoculationAudit.setUpdateBy(SecurityUtils.getUsername());
        int i = epidemicInoculationAuditMapper.updateEpidemicInoculationAudit(epidemicInoculationAudit);
        // 如果审核通过
        if (epidemicInoculationAudit.getAuditStatus().equals(AuditStatusEnum.PASS.getCode())) {
            // 添加到疫苗接种记录表

        }
        return i;
    }

    /**
     * 批量删除疫苗接种审核
     *
     * @param auditIds 需要删除的疫苗接种审核主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicInoculationAuditByAuditIds(Long[] auditIds) {
        return epidemicInoculationAuditMapper.deleteEpidemicInoculationAuditByAuditIds(auditIds);
    }

    /**
     * 删除疫苗接种审核信息
     *
     * @param auditId 疫苗接种审核主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicInoculationAuditByAuditId(Long auditId) {
        return epidemicInoculationAuditMapper.deleteEpidemicInoculationAuditByAuditId(auditId);
    }
}
