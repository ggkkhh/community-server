package com.roydon.business.epidemic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.epidemic.domain.entity.EpidemicInoculationHistory;

import java.util.List;

/**
 * 疫苗接种记录Mapper接口
 *
 * @author roydon
 * @date 2023-08-15
 */
public interface EpidemicInoculationHistoryMapper extends BaseMapper<EpidemicInoculationHistory> {

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
     * 删除疫苗接种记录
     *
     * @param historyId 疫苗接种记录主键
     * @return 结果
     */
    int deleteEpidemicInoculationHistoryByHistoryId(Long historyId);

    /**
     * 批量删除疫苗接种记录
     *
     * @param historyIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteEpidemicInoculationHistoryByHistoryIds(Long[] historyIds);
}
