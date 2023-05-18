package com.roydon.business.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.news.entity.MallGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (MallGoods)表数据库访问层
 *
 * @author roydon
 * @since 2023-05-18 23:04:34
 */
public interface MallGoodsMapper extends BaseMapper<MallGoods> {

    /**
     * 通过ID查询单条数据
     *
     * @param goodsId 主键
     * @return 实例对象
     */
    MallGoods queryById(String goodsId);

    /**
     * 查询指定行数据
     *
     * @param mallGoods 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<MallGoods> queryAllByLimit(MallGoods mallGoods, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param mallGoods 查询条件
     * @return 总行数
     */
    long count(MallGoods mallGoods);

    /**
     * 新增数据
     *
     * @param mallGoods 实例对象
     * @return 影响行数
     */
    int insert(MallGoods mallGoods);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MallGoods> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MallGoods> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MallGoods> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MallGoods> entities);

    /**
     * 修改数据
     *
     * @param mallGoods 实例对象
     * @return 影响行数
     */
    int update(MallGoods mallGoods);

    /**
     * 通过主键删除数据
     *
     * @param goodsId 主键
     * @return 影响行数
     */
    int deleteById(String goodsId);

}

