package com.roydon.business.epidemic.controller;

import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.epidemic.domain.dto.EpidemicTemperatureReportPageDTO;
import com.roydon.business.epidemic.domain.entity.EpidemicTemperatureReport;
import com.roydon.business.epidemic.service.IEpidemicTemperatureReportService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.PhoneUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 体温上报Controller
 *
 * @author roydon
 * @date 2023-09-25
 */
@RestController
@RequestMapping("/epidemic/temperature/report")
public class EpidemicTemperatureReportController extends BaseController {

    @Resource
    private IEpidemicTemperatureReportService epidemicTemperatureReportService;

    /**
     * 分页我的体温上报记录
     *
     * @param pageDTO
     * @return
     */
    @PostMapping("/mine_list")
    public AjaxResult mineList(@RequestBody EpidemicTemperatureReportPageDTO pageDTO) {
        IPage<EpidemicTemperatureReport> messageIPage = epidemicTemperatureReportService.queryPageForMine(pageDTO);
        return AjaxResult.genTableData(messageIPage.getRecords(), messageIPage.getTotal());
    }

    /**
     * 查询体温上报列表
     */
    @PreAuthorize("@ss.hasPermi('temperature:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(EpidemicTemperatureReport epidemicTemperatureReport) {
        startPage();
        List<EpidemicTemperatureReport> list = epidemicTemperatureReportService.selectEpidemicTemperatureReportList(epidemicTemperatureReport);
        return getDataTable(list);
    }

    /**
     * 导出体温上报列表
     */
    @PreAuthorize("@ss.hasPermi('temperature:report:export')")
    @Log(title = "体温上报", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EpidemicTemperatureReport epidemicTemperatureReport) {
        List<EpidemicTemperatureReport> list = epidemicTemperatureReportService.selectEpidemicTemperatureReportList(epidemicTemperatureReport);
        ExcelUtil<EpidemicTemperatureReport> util = new ExcelUtil<EpidemicTemperatureReport>(EpidemicTemperatureReport.class);
        util.exportExcel(response, list, "体温上报数据");
    }

    /**
     * 获取体温上报详细信息
     */
    @PreAuthorize("@ss.hasPermi('temperature:report:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(epidemicTemperatureReportService.selectEpidemicTemperatureReportById(id));
    }

    /**
     * 新增体温上报
     */
//    @PreAuthorize("@ss.hasPermi('temperature:report:add')")
    @Log(title = "体温上报", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpidemicTemperatureReport epidemicTemperatureReport) {
        String realName = epidemicTemperatureReport.getRealName();
        if (realName.length() > 5 || StringUtil.isEmpty(realName)) {
            return AjaxResult.error("请输入正确的真实姓名~");
        }
        if (!PhoneUtils.isMobile(epidemicTemperatureReport.getTelephone())) {
            return AjaxResult.error("请输入正确的手机号码~");
        }
        if (!IdcardUtil.isValidCard18(epidemicTemperatureReport.getIdCard())) {
            return AjaxResult.error("请输入正确的身份证号~");
        }
        if (StringUtil.isEmpty(epidemicTemperatureReport.getTemperature())) {
            return AjaxResult.error("请输入体温~");
        }
        double tem = Double.parseDouble(epidemicTemperatureReport.getTemperature());
        if (tem > 42.0 || tem < 36.0) {
            return AjaxResult.error("请输入36~42范围内的体温~");
        }
        // 今日是否已经上报
        LambdaQueryWrapper<EpidemicTemperatureReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(EpidemicTemperatureReport::getCreateTime, DateUtils.getStartOfDay());
        queryWrapper.eq(EpidemicTemperatureReport::getRealName, realName);
        queryWrapper.eq(EpidemicTemperatureReport::getTelephone, epidemicTemperatureReport.getTelephone());
        queryWrapper.eq(EpidemicTemperatureReport::getIdCard, epidemicTemperatureReport.getIdCard());
        List<EpidemicTemperatureReport> one = epidemicTemperatureReportService.list(queryWrapper);
        if (StringUtil.isNotEmpty(one)) {
            return AjaxResult.error("今日已上报，改日再报。");

        }
        return toAjax(epidemicTemperatureReportService.insertEpidemicTemperatureReport(epidemicTemperatureReport));
    }

    /**
     * 修改体温上报
     */
    @PreAuthorize("@ss.hasPermi('temperature:report:edit')")
    @Log(title = "体温上报", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpidemicTemperatureReport epidemicTemperatureReport) {
        String realName = epidemicTemperatureReport.getRealName();
        if (realName.length() > 5 || StringUtil.isEmpty(realName)) {
            return AjaxResult.error("请输入正确的真实姓名~");
        }
        if (!PhoneUtils.isMobile(epidemicTemperatureReport.getTelephone())) {
            return AjaxResult.error("请输入正确的手机号码~");
        }
        if (!IdcardUtil.isValidCard18(epidemicTemperatureReport.getIdCard())) {
            return AjaxResult.error("请输入正确的身份证号~");
        }
        if (StringUtil.isEmpty(epidemicTemperatureReport.getTemperature())) {
            return AjaxResult.error("请输入体温~");
        }
        double tem = Double.parseDouble(epidemicTemperatureReport.getTemperature());
        if (tem > 42.0 || tem < 36.0) {
            return AjaxResult.error("请输入36~42范围内的体温~");
        }
        return toAjax(epidemicTemperatureReportService.updateEpidemicTemperatureReport(epidemicTemperatureReport));
    }

    /**
     * 删除体温上报
     */
    @PreAuthorize("@ss.hasPermi('temperature:report:remove')")
    @Log(title = "体温上报", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(epidemicTemperatureReportService.deleteEpidemicTemperatureReportByIds(ids));
    }

}
