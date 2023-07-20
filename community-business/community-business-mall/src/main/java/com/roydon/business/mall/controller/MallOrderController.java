package com.roydon.business.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.mall.domain.dto.MallOrderCreateDTO;
import com.roydon.business.mall.domain.dto.MallOrderDTO;
import com.roydon.business.mall.domain.entity.MallOrder;
import com.roydon.business.mall.domain.vo.MallOrderGoodsVO;
import com.roydon.business.mall.domain.vo.MallOrderVO;
import com.roydon.business.mall.service.IMallOrderGoodsService;
import com.roydon.business.mall.service.IMallOrderService;
import com.roydon.business.mall.service.IMallUserAddressService;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.utils.bean.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (MallOrder)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:14:11
 */
@RestController
@RequestMapping("/app/mallOrder")
public class MallOrderController extends BaseController {

    @Resource
    private IMallOrderService mallOrderService;

    @Resource
    private IMallOrderGoodsService mallOrderGoodsService;

    @Resource
    private IMallUserAddressService mallUserAddressService;

    /**
     * 分页查询
     *
     * @param mallOrderDTO mallOrderDTO
     * @return vo
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody MallOrderDTO mallOrderDTO) {
        IPage<MallOrder> mallOrderIPage = mallOrderService.queryPage(mallOrderDTO);
        List<MallOrder> records = mallOrderIPage.getRecords();
        List<MallOrderVO> mallOrderVOList = new ArrayList<>();
        records.forEach(r -> {
            List<MallOrderGoodsVO> oneOrderGoodsByOrderId = mallOrderGoodsService.getOneOrderGoodsByOrderId(r.getOrderId());
            MallOrderVO mallOrderVO = BeanCopyUtils.copyBean(r, MallOrderVO.class);
            // 订单商品
            mallOrderVO.setMallOrderGoodsVOList(oneOrderGoodsByOrderId);
            // 收货地址
            mallOrderVO.setMallUserAddress(mallUserAddressService.getById(r.getAddressId()));
            mallOrderVOList.add(mallOrderVO);
        });
        return AjaxResult.genTableData(mallOrderVOList, mallOrderIPage.getTotal());
    }

    /**
     * 分页查询userOrderList
     *
     * @param mallOrderDTO mallOrderDTO
     * @return vo
     */
    @PostMapping("/userOrderList")
    public AjaxResult userOrderList(@RequestBody MallOrderDTO mallOrderDTO) {
        IPage<MallOrder> mallOrderIPage = mallOrderService.queryPageByToken(mallOrderDTO);
        List<MallOrder> records = mallOrderIPage.getRecords();
        List<MallOrderVO> mallOrderVOList = new ArrayList<>();
        records.forEach(r -> {
            List<MallOrderGoodsVO> oneOrderGoodsByOrderId = mallOrderGoodsService.getOneOrderGoodsByOrderId(r.getOrderId());
            MallOrderVO mallOrderVO = BeanCopyUtils.copyBean(r, MallOrderVO.class);
            // 订单商品
            mallOrderVO.setMallOrderGoodsVOList(oneOrderGoodsByOrderId);
            // 收货地址
            mallOrderVO.setMallUserAddress(mallUserAddressService.getById(r.getAddressId()));
            mallOrderVOList.add(mallOrderVO);
        });
        return AjaxResult.genTableData(mallOrderVOList, mallOrderIPage.getTotal());
    }

    /**
     * 用户删除订单（逻辑删除）
     *
     * @param orderIds
     * @return AjaxResult
     */
    @DeleteMapping("/userRemoveOrder/{orderIds}")
    public AjaxResult userRemoveOneOrder(@PathVariable String[] orderIds) {
        return toAjax(mallOrderService.removeOrderByIds(orderIds));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.mallOrderService.queryById(id));
    }

    /**
     * 创建订单
     *
     * @param mallOrderCreateDTO
     * @return
     */
    @PostMapping("/create")
    public AjaxResult createOrder(@RequestBody MallOrderCreateDTO mallOrderCreateDTO) {
        return AjaxResult.success(mallOrderService.createOrder(mallOrderCreateDTO));
    }

    /**
     * 编辑数据
     *
     * @param mallOrder 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MallOrder mallOrder) {
        return AjaxResult.success(this.mallOrderService.update(mallOrder));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult removeById(String id) {
        return AjaxResult.success(this.mallOrderService.deleteById(id));
    }

}

