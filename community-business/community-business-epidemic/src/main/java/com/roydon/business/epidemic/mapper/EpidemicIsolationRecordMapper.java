package com.roydon.business.epidemic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.epidemic.domain.entity.EpidemicIsolationRecord;

import java.util.List;

/**
 * 隔离记录Mapper接口
 *
 * @author roydon
 * @date 2023-08-09
 */
public interface EpidemicIsolationRecordMapper extends BaseMapper<EpidemicIsolationRecord> {

    /**
     * 查询隔离记录
     *
     * @param recordId 隔离记录主键
     * @return 隔离记录
     */
     EpidemicIsolationRecord selectEpidemicIsolationRecordByRecordId(Long recordId);

    /**
     * 查询隔离记录列表
     *
     * @param epidemicIsolationRecord 隔离记录
     * @return 隔离记录集合
     */
    List<EpidemicIsolationRecord> selectEpidemicIsolationRecordList(EpidemicIsolationRecord epidemicIsolationRecord);

    /**
     * 新增隔离记录
     *
     * @param epidemicIsolationRecord 隔离记录
     * @return 结果
     */
    int insertEpidemicIsolationRecord(EpidemicIsolationRecord epidemicIsolationRecord);

    /**
     * 修改隔离记录
     *
     * @param epidemicIsolationRecord 隔离记录
     * @return 结果
     */
    int updateEpidemicIsolationRecord(EpidemicIsolationRecord epidemicIsolationRecord);

    /**
     * 删除隔离记录
     *
     * @param recordId 隔离记录主键
     * @return 结果
     */
    int deleteEpidemicIsolationRecordByRecordId(Long recordId);

    /**
     * 批量删除隔离记录
     *
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteEpidemicIsolationRecordByRecordIds(Long[] recordIds);
}
