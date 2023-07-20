package com.roydon.business.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.mall.domain.dto.MallGoodsDTO;
import com.roydon.business.mall.domain.entity.MallGoods;
import com.roydon.business.mall.domain.vo.MallGoodsVO;
import com.roydon.business.mall.service.IMallGoodsService;
import com.roydon.business.oss.service.OssService;
import com.roydon.common.annotation.Log;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.bean.BeanCopyUtils;
import com.roydon.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Resource
    private OssService ossService;

    /**
     * 分页查询
     *
     * @param mallGoodsDTO
     * @return
     */
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
     * 获取商品详细信息
     *
     * @param goodsId goodsId
     * @return MallGoodsVO
     */
    @GetMapping("/{goodsId}")
    public AjaxResult queryById(@PathVariable("goodsId") String goodsId) {
        MallGoods mallGoods = mallGoodsService.getById(goodsId);
        SysUser sysUser = sysUserService.selectUserById(mallGoods.getUserId());
        MallGoodsVO mallGoodsVO = BeanCopyUtils.copyBean(mallGoods, MallGoodsVO.class);
        mallGoodsVO.setNickName(sysUser.getNickName());
        mallGoodsVO.setAvatar(sysUser.getAvatar());
        return AjaxResult.success(mallGoodsVO);
    }

    /**
     * 新增数据
     *
     * @param mallGoods 实体
     * @return 新增结果
     */
    @Log(title = "商品管理", businessType = BusinessType.INSERT)
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
    @Log(title = "商品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallGoods mallGoods) {
        return AjaxResult.success(this.mallGoodsService.update(mallGoods));
    }

    /**
     * 删除数据
     *
     * @param goodsIds 主键
     * @return 删除是否成功
     */
    @Log(title = "商品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{goodsIds}")
    public AjaxResult removeById(@PathVariable String[] goodsIds) {
        return AjaxResult.success(this.mallGoodsService.deleteByIds(goodsIds));
    }

    /**
     * 商品封面上传
     */
    @ApiOperation("商品封面上传")
    @Log(title = "商品封面上传", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadImg")
    public AjaxResult avatar(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String imgUrl = ossService.uploadGoodsImgFile(file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", imgUrl);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    @ApiOperation("商品状态")
    @Log(title = "商品管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody MallGoods mallGoods) {
        return AjaxResult.success(mallGoodsService.changeStatus(mallGoods));
    }

}

