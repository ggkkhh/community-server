package com.roydon.business.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.mall.domain.entity.MallCategory;
import com.roydon.business.mall.mapper.MallCategoryMapper;
import com.roydon.business.mall.service.IMallCategoryService;
import com.roydon.common.constant.UserConstants;
import com.roydon.common.exception.ServiceException;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品分类Service业务层处理
 *
 * @author roydon
 * @date 2023-08-05
 */
@Service
public class MallCategoryServiceImpl extends ServiceImpl<MallCategoryMapper, MallCategory> implements IMallCategoryService {

    @Resource
    private MallCategoryMapper mallCategoryMapper;

    /**
     * 查询商品分类
     *
     * @param categoryId 商品分类主键
     * @return 商品分类
     */
    @Override
    public MallCategory selectMallCategoryByCategoryId(Long categoryId) {
        return mallCategoryMapper.selectMallCategoryByCategoryId(categoryId);
    }

    @Override
    public List<MallCategory> selectCategoryList(MallCategory mallCategory) {
        LambdaQueryWrapper<MallCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtil.isNotEmpty(mallCategory.getCategoryId()), MallCategory::getCategoryId, mallCategory.getCategoryId())
                .eq(StringUtil.isNotEmpty(mallCategory.getParentId()), MallCategory::getParentId, mallCategory.getParentId())
                .eq(StringUtil.isNotEmpty(mallCategory.getCategoryName()), MallCategory::getCategoryName, mallCategory.getCategoryName())
                .eq(StringUtil.isNotEmpty(mallCategory.getStatus()), MallCategory::getStatus, mallCategory.getStatus())
                .eq(MallCategory::getDelFlag, "0")
                .orderByAsc(MallCategory::getParentId, MallCategory::getOrderNum);
        return list(queryWrapper);
    }

    @Override
    public String checkCategoryNameUnique(MallCategory mallCategory) {
        Long categoryId = StringUtils.isNull(mallCategory.getCategoryId()) ? -1L : mallCategory.getCategoryId();
        LambdaQueryWrapper<MallCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MallCategory::getCategoryName, mallCategory.getCategoryName()).eq(MallCategory::getParentId, mallCategory.getParentId()).eq(MallCategory::getDelFlag, "0");
        MallCategory one = getOne(queryWrapper);
        if (StringUtils.isNotNull(one) && one.getCategoryId().longValue() != categoryId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertCategory(MallCategory mallCategory) {
        if (StringUtil.isEmpty(mallCategory.getParentId())) {
            mallCategory.setAncestors("0");
        } else {
            MallCategory parent = this.getById(mallCategory.getParentId());
            // 如果父节点被禁用,则不允许新增子节点
            if (UserConstants.DEPT_DISABLE.equals(parent.getStatus())) {
                throw new ServiceException("分类被停用，不允许新增");
            }
            mallCategory.setAncestors(parent.getAncestors() + "," + mallCategory.getParentId());
        }
        return mallCategoryMapper.insertMallCategory(mallCategory);
    }

    @Override
    public int selectNormalChildrenCategoryById(Long categoryId) {
        return mallCategoryMapper.selectNormalChildrenCategoryById(categoryId);
    }

    @Override
    public boolean hasChildByCategoryId(Long categoryId) {
        LambdaQueryWrapper<MallCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MallCategory::getParentId, categoryId).eq(MallCategory::getDelFlag, "0");
        return StringUtil.isNotEmpty(getOne(queryWrapper));
    }

    /**
     * 查询商品分类列表
     *
     * @param mallCategory 商品分类
     * @return 商品分类
     */
    @Override
    public List<MallCategory> selectMallCategoryList(MallCategory mallCategory) {
        return mallCategoryMapper.selectMallCategoryList(mallCategory);
    }

    /**
     * 新增商品分类
     *
     * @param mallCategory 商品分类
     * @return 结果
     */
    @Override
    public int insertMallCategory(MallCategory mallCategory) {
        mallCategory.setCreateTime(DateUtils.getNowDate());
        return mallCategoryMapper.insertMallCategory(mallCategory);
    }

    /**
     * 修改商品分类
     *
     * @param mallCategory 商品分类
     * @return 结果
     */
    @Override
    public int updateMallCategory(MallCategory mallCategory) {
        mallCategory.setUpdateTime(DateUtils.getNowDate());
        return mallCategoryMapper.updateMallCategory(mallCategory);
    }

    /**
     * 批量删除商品分类
     *
     * @param categoryIds 需要删除的商品分类主键
     * @return 结果
     */
    @Override
    public int deleteMallCategoryByCategoryIds(Long[] categoryIds) {
        return mallCategoryMapper.deleteMallCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除商品分类信息
     *
     * @param categoryId 商品分类主键
     * @return 结果
     */
    @Override
    public int deleteMallCategoryByCategoryId(Long categoryId) {
        return mallCategoryMapper.deleteMallCategoryByCategoryId(categoryId);
    }
}
