package com.roydon.business.epidemic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.epidemic.domain.entity.EpidemicTemperatureReport;

import java.util.List;

/**
 * 体温上报Mapper接口
 *
 * @author roydon
 * @date 2023-09-25
 */
public interface EpidemicTemperatureReportMapper extends BaseMapper<EpidemicTemperatureReport> {

    /**
     * 查询体温上报
     *
     * @param id 体温上报主键
     * @return 体温上报
     */
    EpidemicTemperatureReport selectEpidemicTemperatureReportById(Long id);

    /**
     * 查询体温上报列表
     *
     * @param epidemicTemperatureReport 体温上报
     * @return 体温上报集合
     */
    List<EpidemicTemperatureReport> selectEpidemicTemperatureReportList(EpidemicTemperatureReport epidemicTemperatureReport);

    /**
     * 新增体温上报
     *
     * @param epidemicTemperatureReport 体温上报
     * @return 结果
     */
    int insertEpidemicTemperatureReport(EpidemicTemperatureReport epidemicTemperatureReport);

    /**
     * 修改体温上报
     *
     * @param epidemicTemperatureReport 体温上报
     * @return 结果
     */
    int updateEpidemicTemperatureReport(EpidemicTemperatureReport epidemicTemperatureReport);

    /**
     * 删除体温上报
     *
     * @param id 体温上报主键
     * @return 结果
     */
    int deleteEpidemicTemperatureReportById(Long id);

    /**
     * 批量删除体温上报
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteEpidemicTemperatureReportByIds(Long[] ids);
}
