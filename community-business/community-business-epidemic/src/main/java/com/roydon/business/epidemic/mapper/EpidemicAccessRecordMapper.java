package com.roydon.business.epidemic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.business.epidemic.domain.entity.EpidemicAccessRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出入社区人员记录Mapper接口
 *
 * @author roydon
 * @date 2023-05-26
 */
@Mapper
public interface EpidemicAccessRecordMapper extends BaseMapper<EpidemicAccessRecord> {
    /**
     * 查询出入社区人员记录
     *
     * @param recordId 出入社区人员记录主键
     * @return 出入社区人员记录
     */
    public EpidemicAccessRecord selectEpidemicAccessRecordByRecordId(String recordId);

    /**
     * 查询出入社区人员记录列表
     *
     * @param epidemicAccessRecord 出入社区人员记录
     * @return 出入社区人员记录集合
     */
    public List<EpidemicAccessRecord> selectEpidemicAccessRecordList(EpidemicAccessRecord epidemicAccessRecord);

    /**
     * 新增出入社区人员记录
     *
     * @param epidemicAccessRecord 出入社区人员记录
     * @return 结果
     */
    public int insertEpidemicAccessRecord(EpidemicAccessRecord epidemicAccessRecord);

    /**
     * 修改出入社区人员记录
     *
     * @param epidemicAccessRecord 出入社区人员记录
     * @return 结果
     */
    public int updateEpidemicAccessRecord(EpidemicAccessRecord epidemicAccessRecord);

    /**
     * 删除出入社区人员记录
     *
     * @param recordId 出入社区人员记录主键
     * @return 结果
     */
    public int deleteEpidemicAccessRecordByRecordId(String recordId);

    /**
     * 批量删除出入社区人员记录
     *
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEpidemicAccessRecordByRecordIds(String[] recordIds);
}
