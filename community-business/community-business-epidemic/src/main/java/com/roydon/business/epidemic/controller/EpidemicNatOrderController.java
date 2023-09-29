package com.roydon.business.epidemic.controller;

import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.epidemic.domain.dto.EpidemicNatOrderPageDTO;
import com.roydon.business.epidemic.domain.entity.EpidemicNatOrder;
import com.roydon.business.epidemic.service.IEpidemicNatOrderService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.page.TableDataInfo;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.PhoneUtils;
import com.roydon.common.utils.StringUtils;
import com.roydon.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 预约核酸检测NATController
 *
 * @author roydon
 * @date 2023-08-23
 */
@RestController
@RequestMapping("/epidemic/nat/order")
public class EpidemicNatOrderController extends BaseController {

    @Resource
    private IEpidemicNatOrderService epidemicNatOrderService;

    @PostMapping("/mine_history")
    public AjaxResult myOrderRecord(@RequestBody EpidemicNatOrderPageDTO pageDTO) {
        IPage<EpidemicNatOrder> natOrderIPage = epidemicNatOrderService.queryPageForMine(pageDTO);
        return AjaxResult.genTableData(natOrderIPage.getRecords(), natOrderIPage.getTotal());
    }

    /**
     * app端一键预约核酸检测
     *
     * @param epidemicNatOrder nat实体
     * @return AjaxResult
     */
    @PostMapping("/quick")
    public AjaxResult quickOrder(@RequestBody EpidemicNatOrder epidemicNatOrder) {
        String telephone = epidemicNatOrder.getTelephone();
        String idCard = epidemicNatOrder.getIdCard();
        if (!PhoneUtils.isMobile(telephone)) {
            return AjaxResult.error("请输入正确的手机号");
        }
        if (!IdcardUtil.isValidCard(idCard)) {
            return AjaxResult.error("请输入正确的身份证号");
        }
        // 一天只许预约一次
        LambdaQueryWrapper<EpidemicNatOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EpidemicNatOrder::getTelephone, telephone);
        queryWrapper.eq(EpidemicNatOrder::getIdCard, idCard);
        queryWrapper.between(EpidemicNatOrder::getOrderTime, DateUtils.getStartOfDay(), DateUtils.getStartOfNextDay());
        List<EpidemicNatOrder> historyList = epidemicNatOrderService.list(queryWrapper);
        if (StringUtils.isNotEmpty(historyList)) {
            return AjaxResult.error("今日已预约，明日再来~");
        }
        return toAjax(epidemicNatOrderService.insertEpidemicNatOrder(epidemicNatOrder));
    }

    /**
     * 查询预约核酸检测NAT列表
     */
    @PreAuthorize("@ss.hasPermi('nat:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(EpidemicNatOrder epidemicNatOrder) {
        startPage();
        List<EpidemicNatOrder> list = epidemicNatOrderService.selectEpidemicNatOrderList(epidemicNatOrder);
        return getDataTable(list);
    }

    /**
     * 导出预约核酸检测NAT列表
     */
    @PreAuthorize("@ss.hasPermi('nat:order:export')")
    @Log(title = "预约核酸检测NAT", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EpidemicNatOrder epidemicNatOrder) {
        List<EpidemicNatOrder> list = epidemicNatOrderService.selectEpidemicNatOrderList(epidemicNatOrder);
        ExcelUtil<EpidemicNatOrder> util = new ExcelUtil<EpidemicNatOrder>(EpidemicNatOrder.class);
        util.exportExcel(response, list, "预约核酸检测NAT数据");
    }

    /**
     * 获取预约核酸检测NAT详细信息
     */
    @PreAuthorize("@ss.hasPermi('nat:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        return success(epidemicNatOrderService.selectEpidemicNatOrderByOrderId(orderId));
    }

    /**
     * 新增预约核酸检测NAT
     */
//    @PreAuthorize("@ss.hasPermi('nat:order:add')")
    @Log(title = "预约核酸检测NAT", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EpidemicNatOrder epidemicNatOrder) {
        String telephone = epidemicNatOrder.getTelephone();
        String idCard = epidemicNatOrder.getIdCard();
        if (!PhoneUtils.isMobile(telephone)) {
            return AjaxResult.error("请输入正确的手机号");
        }
        if (!IdcardUtil.isValidCard(idCard)) {
            return AjaxResult.error("请输入正确的身份证号");
        }
        // 一天只许预约一次
        LambdaQueryWrapper<EpidemicNatOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EpidemicNatOrder::getTelephone, telephone);
        queryWrapper.eq(EpidemicNatOrder::getIdCard, idCard);
        queryWrapper.between(EpidemicNatOrder::getOrderTime, DateUtils.getStartOfDay(), DateUtils.getStartOfNextDay());
        List<EpidemicNatOrder> historyList = epidemicNatOrderService.list(queryWrapper);
        if (StringUtils.isNotEmpty(historyList)) {
            return AjaxResult.error("今日已预约，明日再来~");
        }
        return toAjax(epidemicNatOrderService.insertEpidemicNatOrder(epidemicNatOrder));
    }

    /**
     * 修改预约核酸检测NAT
     */
    @PreAuthorize("@ss.hasPermi('nat:order:edit')")
    @Log(title = "预约核酸检测NAT", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EpidemicNatOrder epidemicNatOrder) {
        return toAjax(epidemicNatOrderService.updateEpidemicNatOrder(epidemicNatOrder));
    }

    /**
     * 删除预约核酸检测NAT
     */
    @PreAuthorize("@ss.hasPermi('nat:order:remove')")
    @Log(title = "预约核酸检测NAT", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(epidemicNatOrderService.deleteEpidemicNatOrderByOrderIds(orderIds));
    }
}
