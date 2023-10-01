package com.roydon.business.epidemic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.epidemic.domain.dto.EpidemicTemperatureReportPageDTO;
import com.roydon.business.epidemic.domain.entity.EpidemicTemperatureReport;
import com.roydon.business.epidemic.mapper.EpidemicTemperatureReportMapper;
import com.roydon.business.epidemic.service.IEpidemicTemperatureReportService;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 体温上报Service业务层处理
 *
 * @author roydon
 * @date 2023-09-25
 */
@Service
public class EpidemicTemperatureReportServiceImpl extends ServiceImpl<EpidemicTemperatureReportMapper, EpidemicTemperatureReport> implements IEpidemicTemperatureReportService {

    @Resource
    private EpidemicTemperatureReportMapper epidemicTemperatureReportMapper;

    /**
     * 分页查询我的上报记录
     *
     * @param pageDTO
     * @return
     */
    @Override
    public IPage<EpidemicTemperatureReport> queryPageForMine(EpidemicTemperatureReportPageDTO pageDTO) {
        LambdaQueryWrapper<EpidemicTemperatureReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EpidemicTemperatureReport::getUsername, SecurityUtils.getUsername());
        queryWrapper.orderByDesc(EpidemicTemperatureReport::getCreateTime);
        return page(new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize()), queryWrapper);
    }

    /**
     * 查询体温上报
     *
     * @param id 体温上报主键
     * @return 体温上报
     */
    @Override
    public EpidemicTemperatureReport selectEpidemicTemperatureReportById(Long id) {
        return epidemicTemperatureReportMapper.selectEpidemicTemperatureReportById(id);
    }

    /**
     * 查询体温上报列表
     *
     * @param epidemicTemperatureReport 体温上报
     * @return 体温上报
     */
    @Override
    public List<EpidemicTemperatureReport> selectEpidemicTemperatureReportList(EpidemicTemperatureReport epidemicTemperatureReport) {
        return epidemicTemperatureReportMapper.selectEpidemicTemperatureReportList(epidemicTemperatureReport);
    }

    /**
     * 新增体温上报
     *
     * @param epidemicTemperatureReport 体温上报
     * @return 结果
     */
    @Override
    public int insertEpidemicTemperatureReport(EpidemicTemperatureReport epidemicTemperatureReport) {
        epidemicTemperatureReport.setCreateTime(DateUtils.getNowDate());
        epidemicTemperatureReport.setCreateBy(SecurityUtils.getUsername());
        epidemicTemperatureReport.setUsername(SecurityUtils.getUsername());
        return epidemicTemperatureReportMapper.insertEpidemicTemperatureReport(epidemicTemperatureReport);
    }

    /**
     * 修改体温上报
     *
     * @param epidemicTemperatureReport 体温上报
     * @return 结果
     */
    @Override
    public int updateEpidemicTemperatureReport(EpidemicTemperatureReport epidemicTemperatureReport) {
        epidemicTemperatureReport.setUpdateTime(DateUtils.getNowDate());
        epidemicTemperatureReport.setUpdateBy(SecurityUtils.getUsername());
        return epidemicTemperatureReportMapper.updateEpidemicTemperatureReport(epidemicTemperatureReport);
    }

    /**
     * 批量删除体温上报
     *
     * @param ids 需要删除的体温上报主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicTemperatureReportByIds(Long[] ids) {
        return epidemicTemperatureReportMapper.deleteEpidemicTemperatureReportByIds(ids);
    }

    /**
     * 删除体温上报信息
     *
     * @param id 体温上报主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicTemperatureReportById(Long id) {
        return epidemicTemperatureReportMapper.deleteEpidemicTemperatureReportById(id);
    }
}
