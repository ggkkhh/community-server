package com.roydon.business.epidemic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.epidemic.domain.entity.EpidemicHealthCode;

import java.util.List;

/**
 * 居民健康码Mapper接口
 *
 * @author roydon
 * @date 2023-08-08
 */
public interface EpidemicHealthCodeMapper extends BaseMapper<EpidemicHealthCode> {

    /**
     * 查询居民健康码
     *
     * @param codeId 居民健康码主键
     * @return 居民健康码
     */
     EpidemicHealthCode selectEpidemicHealthCodeByCodeId(Long codeId);

    /**
     * 查询居民健康码列表
     *
     * @param epidemicHealthCode 居民健康码
     * @return 居民健康码集合
     */
    List<EpidemicHealthCode> selectEpidemicHealthCodeList(EpidemicHealthCode epidemicHealthCode);

    /**
     * 新增居民健康码
     *
     * @param epidemicHealthCode 居民健康码
     * @return 结果
     */
    int insertEpidemicHealthCode(EpidemicHealthCode epidemicHealthCode);

    /**
     * 修改居民健康码
     *
     * @param epidemicHealthCode 居民健康码
     * @return 结果
     */
    int updateEpidemicHealthCode(EpidemicHealthCode epidemicHealthCode);

    /**
     * 删除居民健康码
     *
     * @param codeId 居民健康码主键
     * @return 结果
     */
    int deleteEpidemicHealthCodeByCodeId(Long codeId);

    /**
     * 批量删除居民健康码
     *
     * @param codeIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteEpidemicHealthCodeByCodeIds(Long[] codeIds);
}
