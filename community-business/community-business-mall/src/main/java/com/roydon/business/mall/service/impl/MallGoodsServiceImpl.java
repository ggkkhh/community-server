package com.roydon.business.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.mall.domain.entity.MallGoods;
import com.roydon.business.mall.domain.dto.MallGoodsDTO;
import com.roydon.business.mall.mapper.MallGoodsMapper;
import com.roydon.business.mall.service.IMallGoodsService;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.uuid.IdUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MallGoods)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:14:18
 */
@Service("mallGoodsService")
public class MallGoodsServiceImpl extends ServiceImpl<MallGoodsMapper, MallGoods> implements IMallGoodsService {
    @Resource
    private MallGoodsMapper mallGoodsMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param goodsId 主键
     * @return 实例对象
     */
    @Override
    public MallGoods queryById(String goodsId) {
        return this.mallGoodsMapper.queryById(goodsId);
    }

    /**
     * 分页查询
     *
     * @param mallGoodsDTO 筛选条件
     * @return 查询结果
     */
    @Override
    public IPage<MallGoods> queryPage(MallGoodsDTO mallGoodsDTO) {
        LambdaQueryWrapper<MallGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotEmpty(mallGoodsDTO.getGoodsTitle()), MallGoods::getGoodsTitle, mallGoodsDTO.getGoodsTitle());

        return page(new Page<>(mallGoodsDTO.getPageNum(), mallGoodsDTO.getPageSize()), queryWrapper);
    }

    /**
     * 新增数据
     *
     * @param mallGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MallGoods insert(MallGoods mallGoods) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        mallGoods.setGoodsId(IdUtils.fastSimpleUUID());
        mallGoods.setUserId(loginUser.getUserId());
        mallGoods.setDeptId(loginUser.getDeptId());
        mallGoods.setViewNum(0);
        this.mallGoodsMapper.insert(mallGoods);
        return mallGoods;
    }

    /**
     * 修改数据
     *
     * @param mallGoods 实例对象
     * @return 实例对象
     */
    @Override
    public MallGoods update(MallGoods mallGoods) {
        this.mallGoodsMapper.update(mallGoods);
        return this.queryById(mallGoods.getGoodsId());
    }

    /**
     * 通过主键删除数据
     *
     * @param goodsId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String goodsId) {
        return this.mallGoodsMapper.deleteById(goodsId) > 0;
    }
}
