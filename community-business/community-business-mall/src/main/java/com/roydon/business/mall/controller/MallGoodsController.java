package com.roydon.business.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.mall.domain.entity.MallGoods;
import com.roydon.business.mall.domain.dto.MallGoodsDTO;
import com.roydon.business.mall.domain.vo.MallGoodsVO;
import com.roydon.business.mall.service.IMallGoodsService;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.utils.bean.BeanCopyUtils;
import com.roydon.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (MallGoods)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:14:18
 */
@RestController
@RequestMapping("/app/mallGoods")
public class MallGoodsController {

    @Resource
    private IMallGoodsService mallGoodsService;

    @Resource
    private ISysUserService sysUserService;

    /**
     * 分页查询
     *
     * @param mallGoodsDTO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mall:goods:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody MallGoodsDTO mallGoodsDTO) {
        IPage<MallGoods> mallGoodsIPage = mallGoodsService.queryPage(mallGoodsDTO);
        List<MallGoods> records = mallGoodsIPage.getRecords();
        List<MallGoodsVO> mallGoodsVOList = new ArrayList<>();
        records.forEach(r -> {
            SysUser sysUser = sysUserService.selectUserById(r.getUserId());
            MallGoodsVO mallGoodsVO = BeanCopyUtils.copyBean(r, MallGoodsVO.class);
            mallGoodsVO.setNickName(sysUser.getNickName());
            mallGoodsVO.setAvatar(sysUser.getAvatar());
            mallGoodsVOList.add(mallGoodsVO);
        });
        return AjaxResult.genTableData(mallGoodsVOList, mallGoodsIPage.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.mallGoodsService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param mallGoods 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MallGoods mallGoods) {
        return AjaxResult.success(this.mallGoodsService.insert(mallGoods));
    }

    /**
     * 编辑数据
     *
     * @param mallGoods 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(MallGoods mallGoods) {
        return AjaxResult.success(this.mallGoodsService.update(mallGoods));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult removeById(String id) {
        return AjaxResult.success(this.mallGoodsService.deleteById(id));
    }

}

