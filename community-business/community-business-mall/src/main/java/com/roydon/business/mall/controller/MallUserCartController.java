package com.roydon.business.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.mall.domain.dto.MallUserCartDTO;
import com.roydon.business.mall.domain.entity.MallGoods;
import com.roydon.business.mall.domain.entity.MallUserCart;
import com.roydon.business.mall.domain.vo.MallUserCartVO;
import com.roydon.business.mall.service.IMallGoodsService;
import com.roydon.business.mall.service.IMallUserCartService;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.utils.bean.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (MallUserCart)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:14:32
 */
@RestController
@RequestMapping("/app/mallUserCart")
public class MallUserCartController {

    @Resource
    private IMallUserCartService mallUserCartService;

    @Resource
    private IMallGoodsService mallGoodsService;

    /**
     * 分页查询
     *
     * @param mallUserCartDTO 分页参数
     * @return MallUserCartVO
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody MallUserCartDTO mallUserCartDTO) {
        IPage<MallUserCart> mallGoodsIPage = mallUserCartService.queryPage(mallUserCartDTO);
        List<MallUserCart> records = mallGoodsIPage.getRecords();
        List<MallUserCartVO> voList = new ArrayList<>();
        records.forEach(r -> {
            String goodsId = r.getGoodsId();
            MallGoods mallGoods = mallGoodsService.getById(goodsId);
            MallUserCartVO mallUserCartVO = BeanCopyUtils.copyBean(r, MallUserCartVO.class);
            mallUserCartVO.setGoodsTitle(mallGoods.getGoodsTitle());
            mallUserCartVO.setGoodsPrice(mallGoods.getGoodsPrice());
            mallUserCartVO.setGoodsImg(mallGoods.getGoodsImg());
            voList.add(mallUserCartVO);
        });
        return AjaxResult.genTableData(voList, mallGoodsIPage.getTotal());
    }

    /**
     * 新增数据
     *
     * @param mallUserCart 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MallUserCart mallUserCart) {
        return AjaxResult.success(mallUserCartService.insert(mallUserCart));
    }

    /**
     * 编辑数据，即购物车商品数量加一，不应该直接操作数据库
     *
     * @param mallUserCart 实体
     * @return 编辑结果
     */
//    @PreAuthorize("@ss.hasPermi('mall:cart:edit')")
//    @PutMapping
//    public AjaxResult edit(@RequestBody MallUserCart mallUserCart) {
//        return AjaxResult.success(mallUserCartService.update(mallUserCart));
//    }

    /**
     * 删除数据
     *
     * @param cartIds 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{cartIds}")
    public AjaxResult removeById(@PathVariable String[] cartIds) {
        return AjaxResult.success(mallUserCartService.deleteByIds(cartIds));
    }

    /**
     * 获取所有数据
     */
    @GetMapping("/all")
    public AjaxResult all() {
        List<MallUserCart> list = mallUserCartService.list();
        List<MallUserCartVO> voList = new ArrayList<>();
        list.forEach(r -> {
            String goodsId = r.getGoodsId();
            MallGoods mallGoods = mallGoodsService.getById(goodsId);
            MallUserCartVO mallUserCartVO = BeanCopyUtils.copyBean(r, MallUserCartVO.class);
            mallUserCartVO.setGoodsTitle(mallGoods.getGoodsTitle());
            mallUserCartVO.setGoodsPrice(mallGoods.getGoodsPrice());
            mallUserCartVO.setGoodsImg(mallGoods.getGoodsImg());
            voList.add(mallUserCartVO);
        });
        return AjaxResult.genTableData(voList, voList.size());
    }

}

