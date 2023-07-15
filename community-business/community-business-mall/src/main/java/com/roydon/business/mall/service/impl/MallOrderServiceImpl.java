package com.roydon.business.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.mall.domain.dto.MallOrderCreateDTO;
import com.roydon.business.mall.domain.dto.MallOrderDTO;
import com.roydon.business.mall.domain.entity.MallGoods;
import com.roydon.business.mall.domain.entity.MallOrder;
import com.roydon.business.mall.domain.entity.MallOrderGoods;
import com.roydon.business.mall.domain.entity.MallUserCart;
import com.roydon.business.mall.mapper.MallOrderMapper;
import com.roydon.business.mall.service.IMallGoodsService;
import com.roydon.business.mall.service.IMallOrderGoodsService;
import com.roydon.business.mall.service.IMallOrderService;
import com.roydon.business.mall.service.IMallUserCartService;
import com.roydon.common.core.domain.model.LoginUser;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.uniqueid.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * (MallOrder)表服务实现类
 *
 * @author roydon
 * @since 2023-05-18 23:14:11
 */
@Service("mallOrderService")
public class MallOrderServiceImpl extends ServiceImpl<MallOrderMapper, MallOrder> implements IMallOrderService {

    @Resource
    private MallOrderMapper mallOrderMapper;

    @Resource
    private IMallUserCartService mallUserCartService;

    @Resource
    private IMallGoodsService mallGoodsService;

    @Resource
    private IMallOrderGoodsService mallOrderGoodsService;

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    @Override
    public MallOrder queryById(String orderId) {
        return getById(orderId);
    }

    /**
     * 分页查询
     *
     * @param mallOrderDTO 筛选条件：1.用户账号2.支付状态
     * @return 查询结果
     */
    @Override
    public IPage<MallOrder> queryPage(MallOrderDTO mallOrderDTO) {
        LambdaQueryWrapper<MallOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotEmpty(mallOrderDTO.getUserName()), MallOrder::getUserName, mallOrderDTO.getUserName());
        queryWrapper.eq(StringUtil.isNotEmpty(mallOrderDTO.getPayStatus()), MallOrder::getPayStatus, mallOrderDTO.getPayStatus());
        queryWrapper.eq(StringUtil.isNotEmpty(mallOrderDTO.getUserId()), MallOrder::getUserId, mallOrderDTO.getUserId());
        queryWrapper.orderByDesc(MallOrder::getCreateTime);
        return page(new Page<>(mallOrderDTO.getPageNum(), mallOrderDTO.getPageSize()), queryWrapper);
    }

    /**
     * 分页查询ByToken
     *
     * @param mallOrderDTO 筛选条件：1.用户账号2.支付状态
     * @return 查询结果
     */
    @Override
    public IPage<MallOrder> queryPageByToken(MallOrderDTO mallOrderDTO) {
        LambdaQueryWrapper<MallOrder> queryWrapper = new LambdaQueryWrapper<>();
        Long userId = SecurityUtils.getUserId();
        queryWrapper.eq(MallOrder::getUserId, userId);
        queryWrapper.eq(MallOrder::getDelFlag, "0");
        queryWrapper.orderByDesc(MallOrder::getCreateTime);
        return page(new Page<>(mallOrderDTO.getPageNum(), mallOrderDTO.getPageSize()), queryWrapper);
    }

    /**
     * 新增数据
     *
     * @param mallOrder 实例对象
     * @return 实例对象
     */
    @Override
    public MallOrder insert(MallOrder mallOrder) {
        this.mallOrderMapper.insert(mallOrder);
        return mallOrder;
    }

    /**
     * 创建订单
     *
     * @param mallOrderCreateDTO
     */
    @Override
    @Transactional
    public MallOrder createOrder(MallOrderCreateDTO mallOrderCreateDTO) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        MallOrder mallOrder = new MallOrder();
        mallOrder.setOrderId(IdGenerator.generatorId());
        mallOrder.setUserId(loginUser.getUserId());
        mallOrder.setUserName(loginUser.getUsername());
        mallOrder.setAddressId(mallOrderCreateDTO.getAddressId());
        mallOrder.setDelFlag("0");
        List<String> goodsIds = mallOrderCreateDTO.getGoodsIds();
        List<MallUserCart> cartList = mallUserCartService.queryListByGoodsId(goodsIds);
        // 订单管理订单商品
        List<MallOrderGoods> mallOrderGoodsList = new ArrayList<>();
        // 计算总价
        AtomicReference<Double> totalPrice = new AtomicReference<>(0.00);
        cartList.forEach(c -> {
            MallGoods goods = mallGoodsService.getById(c.getGoodsId());
            Double price = c.getGoodsCount() * goods.getGoodsPrice();
            totalPrice.updateAndGet(v -> v + price);
            // 订单关联订单商品
            MallOrderGoods mallOrderGoods = new MallOrderGoods();
            mallOrderGoods.setId(IdGenerator.generatorId());
            mallOrderGoods.setOrderId(mallOrder.getOrderId());
            mallOrderGoods.setGoodsId(goods.getGoodsId());
            mallOrderGoods.setPrice(goods.getGoodsPrice());
            mallOrderGoods.setCount(c.getGoodsCount());
            mallOrderGoods.setTotalPrice((Double) totalPrice.get());
            // 默认未收货
            mallOrderGoods.setReceive("0");
            mallOrderGoods.setDelFlag("0");
            mallOrderGoodsList.add(mallOrderGoods);
        });
        mallOrder.setTotalPrice((Double) totalPrice.get());
        // TODO 默认未支付
        mallOrder.setPayStatus("0");
        mallOrder.setPayOrderId(null);
        mallOrder.setCreateTime(new Date());
        mallOrder.setCreateBy(loginUser.getUsername());
        // 订单商品新增
        mallOrderGoodsService.saveBatch(mallOrderGoodsList);
        this.save(mallOrder);
        return mallOrder;
    }

    /**
     * 修改数据
     *
     * @param mallOrder 实例对象
     * @return 实例对象
     */
    @Override
    public MallOrder update(MallOrder mallOrder) {
        update(mallOrder);
        return this.queryById(mallOrder.getOrderId());
    }

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String orderId) {
        return this.mallOrderMapper.deleteById(orderId) > 0;
    }

    /**
     * 批量删除（逻辑删除）
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public boolean removeOrderByIds(String[] orderIds) {
        List<String> orderIdList = Arrays.asList(orderIds);
        try {
            orderIdList.forEach(o -> {
                // 删除mall_order表
                LambdaUpdateWrapper<MallOrder> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(MallOrder::getOrderId, o).set(MallOrder::getDelFlag, "2");
                update(updateWrapper);
                // 删除mall_order_goods表
                LambdaUpdateWrapper<MallOrderGoods> orderGoodsLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                orderGoodsLambdaUpdateWrapper.eq(MallOrderGoods::getOrderId, o).set(MallOrderGoods::getDelFlag, "2");
                mallOrderGoodsService.update(orderGoodsLambdaUpdateWrapper);
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
