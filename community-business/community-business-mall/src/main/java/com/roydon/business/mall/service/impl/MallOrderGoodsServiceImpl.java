package com.roydon.business.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.mall.domain.entity.MallOrderGoods;
import com.roydon.business.mall.domain.vo.MallOrderGoodsVO;
import com.roydon.business.mall.mapper.MallOrderGoodsMapper;
import com.roydon.business.mall.service.IMallGoodsService;
import com.roydon.business.mall.service.IMallOrderGoodsService;
import com.roydon.common.utils.bean.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (MallOrderGoods)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:13:57
 */
@Service("mallOrderGoodsService")
public class MallOrderGoodsServiceImpl extends ServiceImpl<MallOrderGoodsMapper, MallOrderGoods> implements IMallOrderGoodsService {

    @Resource
    private MallOrderGoodsMapper mallOrderGoodsMapper;

    @Resource
    private IMallGoodsService mallGoodsService;

    @Override
    public List<MallOrderGoods> getOrderGoodsByOrderId(String orderId) {
        LambdaQueryWrapper<MallOrderGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MallOrderGoods::getOrderId, orderId);
        return list(queryWrapper);
    }

    @Override
    public List<MallOrderGoodsVO> getOneOrderGoodsByOrderId(String orderId) {
        LambdaQueryWrapper<MallOrderGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MallOrderGoods::getOrderId, orderId);
        List<MallOrderGoods> mallOrderGoodsList = list(queryWrapper);
        List<MallOrderGoodsVO> mallOrderGoodsVOList = new ArrayList<>();
        mallOrderGoodsList.forEach(m -> {
            MallOrderGoodsVO mallOrderGoodsVO = BeanCopyUtils.copyBean(m, MallOrderGoodsVO.class);
            mallOrderGoodsVO.setMallGoods(mallGoodsService.getById(m.getGoodsId()));
            mallOrderGoodsVOList.add(mallOrderGoodsVO);
        });
        return mallOrderGoodsVOList;
    }
}
