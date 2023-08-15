package com.roydon.business.epidemic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.epidemic.domain.entity.EpidemicInoculationHistory;
import com.roydon.business.epidemic.mapper.EpidemicInoculationHistoryMapper;
import com.roydon.business.epidemic.service.IEpidemicInoculationHistoryService;
import com.roydon.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 疫苗接种记录Service业务层处理
 *
 * @author roydon
 * @date 2023-08-15
 */
@Service
public class EpidemicInoculationHistoryServiceImpl extends ServiceImpl<EpidemicInoculationHistoryMapper, EpidemicInoculationHistory> implements IEpidemicInoculationHistoryService {

    @Resource
    private EpidemicInoculationHistoryMapper epidemicInoculationHistoryMapper;

    /**
     * 查询疫苗接种记录
     *
     * @param historyId 疫苗接种记录主键
     * @return 疫苗接种记录
     */
    @Override
    public EpidemicInoculationHistory selectEpidemicInoculationHistoryByHistoryId(Long historyId) {
        return epidemicInoculationHistoryMapper.selectEpidemicInoculationHistoryByHistoryId(historyId);
    }

    /**
     * 查询疫苗接种记录列表
     *
     * @param epidemicInoculationHistory 疫苗接种记录
     * @return 疫苗接种记录
     */
    @Override
    public List<EpidemicInoculationHistory> selectEpidemicInoculationHistoryList(EpidemicInoculationHistory epidemicInoculationHistory) {
        return epidemicInoculationHistoryMapper.selectEpidemicInoculationHistoryList(epidemicInoculationHistory);
    }

    /**
     * 新增疫苗接种记录
     *
     * @param epidemicInoculationHistory 疫苗接种记录
     * @return 结果
     */
    @Override
    public int insertEpidemicInoculationHistory(EpidemicInoculationHistory epidemicInoculationHistory) {
        epidemicInoculationHistory.setCreateTime(DateUtils.getNowDate());
        return epidemicInoculationHistoryMapper.insertEpidemicInoculationHistory(epidemicInoculationHistory);
    }

    /**
     * 修改疫苗接种记录
     *
     * @param epidemicInoculationHistory 疫苗接种记录
     * @return 结果
     */
    @Override
    public int updateEpidemicInoculationHistory(EpidemicInoculationHistory epidemicInoculationHistory) {
        epidemicInoculationHistory.setUpdateTime(DateUtils.getNowDate());
        return epidemicInoculationHistoryMapper.updateEpidemicInoculationHistory(epidemicInoculationHistory);
    }

    /**
     * 批量删除疫苗接种记录
     *
     * @param historyIds 需要删除的疫苗接种记录主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicInoculationHistoryByHistoryIds(Long[] historyIds) {
        return epidemicInoculationHistoryMapper.deleteEpidemicInoculationHistoryByHistoryIds(historyIds);
    }

    /**
     * 删除疫苗接种记录信息
     *
     * @param historyId 疫苗接种记录主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicInoculationHistoryByHistoryId(Long historyId) {
        return epidemicInoculationHistoryMapper.deleteEpidemicInoculationHistoryByHistoryId(historyId);
    }
}
