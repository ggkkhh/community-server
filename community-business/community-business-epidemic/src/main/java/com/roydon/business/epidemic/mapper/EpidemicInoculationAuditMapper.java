package com.roydon.business.epidemic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.epidemic.domain.entity.EpidemicInoculationAudit;

import java.util.List;

/**
 * 疫苗接种审核Mapper接口
 *
 * @author roydon
 * @date 2023-08-10
 */
public interface EpidemicInoculationAuditMapper extends BaseMapper<EpidemicInoculationAudit> {

    /**
     * 查询疫苗接种审核
     *
     * @param auditId 疫苗接种审核主键
     * @return 疫苗接种审核
     */
     EpidemicInoculationAudit selectEpidemicInoculationAuditByAuditId(Long auditId);

    /**
     * 查询疫苗接种审核列表
     *
     * @param epidemicInoculationAudit 疫苗接种审核
     * @return 疫苗接种审核集合
     */
    List<EpidemicInoculationAudit> selectEpidemicInoculationAuditList(EpidemicInoculationAudit epidemicInoculationAudit);

    /**
     * 新增疫苗接种审核
     *
     * @param epidemicInoculationAudit 疫苗接种审核
     * @return 结果
     */
    int insertEpidemicInoculationAudit(EpidemicInoculationAudit epidemicInoculationAudit);

    /**
     * 修改疫苗接种审核
     *
     * @param epidemicInoculationAudit 疫苗接种审核
     * @return 结果
     */
    int updateEpidemicInoculationAudit(EpidemicInoculationAudit epidemicInoculationAudit);

    /**
     * 删除疫苗接种审核
     *
     * @param auditId 疫苗接种审核主键
     * @return 结果
     */
    int deleteEpidemicInoculationAuditByAuditId(Long auditId);

    /**
     * 批量删除疫苗接种审核
     *
     * @param auditIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteEpidemicInoculationAuditByAuditIds(Long[] auditIds);
}
