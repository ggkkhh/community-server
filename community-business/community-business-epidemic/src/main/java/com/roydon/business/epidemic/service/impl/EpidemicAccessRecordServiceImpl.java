package com.roydon.business.epidemic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.epidemic.domain.entity.EpidemicAccessRecord;
import com.roydon.business.epidemic.mapper.EpidemicAccessRecordMapper;
import com.roydon.business.epidemic.service.IEpidemicAccessRecordService;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.uniqueid.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 出入社区人员记录Service业务层处理
 *
 * @author roydon
 * @date 2023-05-26
 */
@Service
public class EpidemicAccessRecordServiceImpl extends ServiceImpl<EpidemicAccessRecordMapper, EpidemicAccessRecord> implements IEpidemicAccessRecordService {

    @Resource
    private EpidemicAccessRecordMapper epidemicAccessRecordMapper;

    /**
     * 查询出入社区人员记录
     *
     * @param recordId 出入社区人员记录主键
     * @return 出入社区人员记录
     */
    @Override
    public EpidemicAccessRecord selectEpidemicAccessRecordByRecordId(String recordId) {
        return epidemicAccessRecordMapper.selectEpidemicAccessRecordByRecordId(recordId);
    }

    /**
     * 查询出入社区人员记录列表
     *
     * @param epidemicAccessRecord 出入社区人员记录
     * @return 出入社区人员记录
     */
    @Override
    public List<EpidemicAccessRecord> selectEpidemicAccessRecordList(EpidemicAccessRecord epidemicAccessRecord) {
        return epidemicAccessRecordMapper.selectEpidemicAccessRecordList(epidemicAccessRecord);
    }

    /**
     * 新增出入社区人员记录
     *
     * @param epidemicAccessRecord 出入社区人员记录
     * @return 结果
     */
    @Override
    public int insertEpidemicAccessRecord(EpidemicAccessRecord epidemicAccessRecord) {
        epidemicAccessRecord.setCreateTime(DateUtils.getNowDate());
        epidemicAccessRecord.setRecordId(IdGenerator.generatorId());
        return epidemicAccessRecordMapper.insertEpidemicAccessRecord(epidemicAccessRecord);
    }

    /**
     * 修改出入社区人员记录
     *
     * @param epidemicAccessRecord 出入社区人员记录
     * @return 结果
     */
    @Override
    public int updateEpidemicAccessRecord(EpidemicAccessRecord epidemicAccessRecord) {
        epidemicAccessRecord.setUpdateTime(DateUtils.getNowDate());
        return epidemicAccessRecordMapper.updateEpidemicAccessRecord(epidemicAccessRecord);
    }

    /**
     * 批量删除出入社区人员记录
     *
     * @param recordIds 需要删除的出入社区人员记录主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicAccessRecordByRecordIds(String[] recordIds) {
        return epidemicAccessRecordMapper.deleteEpidemicAccessRecordByRecordIds(recordIds);
    }

    /**
     * 删除出入社区人员记录信息
     *
     * @param recordId 出入社区人员记录主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicAccessRecordByRecordId(String recordId) {
        return epidemicAccessRecordMapper.deleteEpidemicAccessRecordByRecordId(recordId);
    }
}
