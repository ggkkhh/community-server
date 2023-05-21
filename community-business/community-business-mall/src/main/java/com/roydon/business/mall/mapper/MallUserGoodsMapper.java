package com.roydon.business.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.mall.domain.entity.MallUserGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户具有的商品表(MallUserGoods)表数据库访问层
 *
 * @author roydon
 * @since 2023-05-18 23:14:38
 */
public interface MallUserGoodsMapper extends BaseMapper<MallUserGoods>{

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallUserGoods queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param mallUserGoods 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<MallUserGoods> queryAllByLimit(MallUserGoods mallUserGoods, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param mallUserGoods 查询条件
     * @return 总行数
     */
    long count(MallUserGoods mallUserGoods);

    /**
     * 新增数据
     *
     * @param mallUserGoods 实例对象
     * @return 影响行数
     */
    int insert(MallUserGoods mallUserGoods);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MallUserGoods> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MallUserGoods> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MallUserGoods> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MallUserGoods> entities);

    /**
     * 修改数据
     *
     * @param mallUserGoods 实例对象
     * @return 影响行数
     */
    int update(MallUserGoods mallUserGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

