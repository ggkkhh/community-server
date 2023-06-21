package com.roydon.business.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.mall.domain.entity.MallOrderGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (MallOrderGoods)表数据库访问层
 *
 * @author roydon
 * @since 2023-05-18 23:13:56
 */
public interface MallOrderGoodsMapper extends BaseMapper<MallOrderGoods> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MallOrderGoods queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param mallOrderGoods 查询条件
     * @param pageable       分页对象
     * @return 对象列表
     */
    List<MallOrderGoods> queryAllByLimit(MallOrderGoods mallOrderGoods, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param mallOrderGoods 查询条件
     * @return 总行数
     */
    long count(MallOrderGoods mallOrderGoods);

    /**
     * 新增数据
     *
     * @param mallOrderGoods 实例对象
     * @return 影响行数
     */
    int insert(MallOrderGoods mallOrderGoods);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MallOrderGoods> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MallOrderGoods> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MallOrderGoods> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MallOrderGoods> entities);

    /**
     * 修改数据
     *
     * @param mallOrderGoods 实例对象
     * @return 影响行数
     */
    int update(MallOrderGoods mallOrderGoods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

