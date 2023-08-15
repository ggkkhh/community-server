package com.roydon.business.epidemic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.epidemic.domain.entity.EpidemicInoculationHistory;

import java.util.List;

/**
 * 疫苗接种记录Service接口
 *
 * @author roydon
 * @date 2023-08-15
 */
public interface IEpidemicInoculationHistoryService extends IService<EpidemicInoculationHistory> {
    /**
     * 查询疫苗接种记录
     *
     * @param historyId 疫苗接种记录主键
     * @return 疫苗接种记录
     */
    EpidemicInoculationHistory selectEpidemicInoculationHistoryByHistoryId(Long historyId);

    /**
     * 查询疫苗接种记录列表
     *
     * @param epidemicInoculationHistory 疫苗接种记录
     * @return 疫苗接种记录集合
     */
    List<EpidemicInoculationHistory> selectEpidemicInoculationHistoryList(EpidemicInoculationHistory epidemicInoculationHistory);

    /**
     * 新增疫苗接种记录
     *
     * @param epidemicInoculationHistory 疫苗接种记录
     * @return 结果
     */
    int insertEpidemicInoculationHistory(EpidemicInoculationHistory epidemicInoculationHistory);

    /**
     * 修改疫苗接种记录
     *
     * @param epidemicInoculationHistory 疫苗接种记录
     * @return 结果
     */
    int updateEpidemicInoculationHistory(EpidemicInoculationHistory epidemicInoculationHistory);

    /**
     * 批量删除疫苗接种记录
     *
     * @param historyIds 需要删除的疫苗接种记录主键集合
     * @return 结果
     */
    int deleteEpidemicInoculationHistoryByHistoryIds(Long[] historyIds);

    /**
     * 删除疫苗接种记录信息
     *
     * @param historyId 疫苗接种记录主键
     * @return 结果
     */
    int deleteEpidemicInoculationHistoryByHistoryId(Long historyId);
}
