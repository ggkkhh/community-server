package com.roydon.business.mall.service;

import com.roydon.business.mall.domain.entity.MallOrderGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (MallOrderGoods)表服务接口
 *
 * @author roydon
 * @since 2023-05-18 23:13:57
 */
public interface IMallOrderGoodsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallOrderGoods queryById(String id);

    /**
     * 分页查询
     *
     * @param mallOrderGoods 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<MallOrderGoods> queryByPage(MallOrderGoods mallOrderGoods, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param mallOrderGoods 实例对象
     * @return 实例对象
     */
    MallOrderGoods insert(MallOrderGoods mallOrderGoods);

    /**
     * 修改数据
     *
     * @param mallOrderGoods 实例对象
     * @return 实例对象
     */
    MallOrderGoods update(MallOrderGoods mallOrderGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
