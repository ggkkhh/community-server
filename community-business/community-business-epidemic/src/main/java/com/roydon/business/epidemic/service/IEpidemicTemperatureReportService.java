package com.roydon.business.epidemic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.epidemic.domain.dto.EpidemicTemperatureReportPageDTO;
import com.roydon.business.epidemic.domain.entity.EpidemicTemperatureReport;

import java.util.List;

/**
 * 体温上报Service接口
 *
 * @author roydon
 * @date 2023-09-25
 */
public interface IEpidemicTemperatureReportService extends IService<EpidemicTemperatureReport> {

    /**
     * 分页查询我的上报记录
     *
     * @param pageDTO
     * @return
     */
    IPage<EpidemicTemperatureReport> queryPageForMine(EpidemicTemperatureReportPageDTO pageDTO);

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
     * 批量删除体温上报
     *
     * @param ids 需要删除的体温上报主键集合
     * @return 结果
     */
    int deleteEpidemicTemperatureReportByIds(Long[] ids);

    /**
     * 删除体温上报信息
     *
     * @param id 体温上报主键
     * @return 结果
     */
    int deleteEpidemicTemperatureReportById(Long id);
}
