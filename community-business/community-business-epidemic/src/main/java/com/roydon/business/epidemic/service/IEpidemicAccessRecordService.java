package com.roydon.business.epidemic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.epidemic.domain.entity.EpidemicAccessRecord;

import java.util.List;

/**
 * 出入社区人员记录Service接口
 *
 * @author roydon
 * @date 2023-05-26
 */
public interface IEpidemicAccessRecordService extends IService<EpidemicAccessRecord> {
    /**
     * 查询出入社区人员记录
     *
     * @param recordId 出入社区人员记录主键
     * @return 出入社区人员记录
     */
    EpidemicAccessRecord selectEpidemicAccessRecordByRecordId(String recordId);

    /**
     * 查询出入社区人员记录列表
     *
     * @param epidemicAccessRecord 出入社区人员记录
     * @return 出入社区人员记录集合
     */
    List<EpidemicAccessRecord> selectEpidemicAccessRecordList(EpidemicAccessRecord epidemicAccessRecord);

    /**
     * 新增出入社区人员记录
     *
     * @param epidemicAccessRecord 出入社区人员记录
     * @return 结果
     */
    int insertEpidemicAccessRecord(EpidemicAccessRecord epidemicAccessRecord);

    /**
     * 修改出入社区人员记录
     *
     * @param epidemicAccessRecord 出入社区人员记录
     * @return 结果
     */
    int updateEpidemicAccessRecord(EpidemicAccessRecord epidemicAccessRecord);

    /**
     * 批量删除出入社区人员记录
     *
     * @param recordIds 需要删除的出入社区人员记录主键集合
     * @return 结果
     */
    int deleteEpidemicAccessRecordByRecordIds(String[] recordIds);

    /**
     * 删除出入社区人员记录信息
     *
     * @param recordId 出入社区人员记录主键
     * @return 结果
     */
    int deleteEpidemicAccessRecordByRecordId(String recordId);
}
