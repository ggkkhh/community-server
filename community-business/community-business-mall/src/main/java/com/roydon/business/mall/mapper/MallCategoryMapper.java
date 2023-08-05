package com.roydon.business.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.mall.domain.entity.MallCategory;

import java.util.List;

/**
 * 商品分类Mapper接口
 *
 * @author roydon
 * @date 2023-08-05
 */
public interface MallCategoryMapper extends BaseMapper<MallCategory> {

    /**
     * 查询商品分类
     *
     * @param categoryId 商品分类主键
     * @return 商品分类
     */
    MallCategory selectMallCategoryByCategoryId(Long categoryId);

    int selectNormalChildrenCategoryById(Long categoryId);

    /**
     * 查询商品分类列表
     *
     * @param mallCategory 商品分类
     * @return 商品分类集合
     */
    List<MallCategory> selectMallCategoryList(MallCategory mallCategory);

    /**
     * 新增商品分类
     *
     * @param mallCategory 商品分类
     * @return 结果
     */
    int insertMallCategory(MallCategory mallCategory);

    /**
     * 修改商品分类
     *
     * @param mallCategory 商品分类
     * @return 结果
     */
    int updateMallCategory(MallCategory mallCategory);

    /**
     * 删除商品分类
     *
     * @param categoryId 商品分类主键
     * @return 结果
     */
    int deleteMallCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除商品分类
     *
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMallCategoryByCategoryIds(Long[] categoryIds);
}
