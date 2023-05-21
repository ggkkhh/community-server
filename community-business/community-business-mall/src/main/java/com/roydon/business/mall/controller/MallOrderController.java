package com.roydon.business.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.mall.domain.dto.MallOrderDTO;
import com.roydon.business.mall.domain.entity.MallOrder;
import com.roydon.business.mall.domain.vo.MallOrderGoodsVO;
import com.roydon.business.mall.domain.vo.MallOrderVO;
import com.roydon.business.mall.service.IMallOrderGoodsService;
import com.roydon.business.mall.service.IMallOrderService;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.utils.bean.BeanCopyUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class MallOrderController {

    @Resource
    private IMallOrderService mallOrderService;

    @Resource
    private IMallOrderGoodsService mallOrderGoodsService;

    /**
     * 分页查询
     *
     * @param mallOrderDTO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mall:order:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody MallOrderDTO mallOrderDTO) {
        IPage<MallOrder> mallOrderIPage = mallOrderService.queryPage(mallOrderDTO);
        List<MallOrder> records = mallOrderIPage.getRecords();
        List<MallOrderVO> mallOrderVOList = new ArrayList<>();
        records.forEach(r->{
            List<MallOrderGoodsVO> oneOrderGoodsByOrderId = mallOrderGoodsService.getOneOrderGoodsByOrderId(r.getOrderId());
            MallOrderVO mallOrderVO = BeanCopyUtils.copyBean(r, MallOrderVO.class);
            mallOrderVO.setMallOrderGoodsVOList(oneOrderGoodsByOrderId);
            mallOrderVOList.add(mallOrderVO);
        });
        return AjaxResult.genTableData(mallOrderVOList, mallOrderIPage.getTotal());
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
     * 新增数据
     *
     * @param mallOrder 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(MallOrder mallOrder) {
        return AjaxResult.success(this.mallOrderService.insert(mallOrder));
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

