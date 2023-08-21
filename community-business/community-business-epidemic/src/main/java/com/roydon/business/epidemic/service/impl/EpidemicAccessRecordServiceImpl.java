package com.roydon.business.epidemic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.epidemic.domain.entity.EpidemicAccessRecord;
import com.roydon.business.epidemic.domain.entity.EpidemicIsolationPolicy;
import com.roydon.business.epidemic.domain.entity.EpidemicIsolationRecord;
import com.roydon.business.epidemic.enums.AccessTypeEnum;
import com.roydon.business.epidemic.mapper.EpidemicAccessRecordMapper;
import com.roydon.business.epidemic.service.IEpidemicAccessRecordService;
import com.roydon.business.epidemic.service.IEpidemicIsolationPolicyService;
import com.roydon.business.epidemic.service.IEpidemicIsolationRecordService;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.redis.RedisCache;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.uniqueid.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private IEpidemicIsolationRecordService epidemicIsolationRecordService;

    @Resource
    private IEpidemicIsolationPolicyService epidemicIsolationPolicyService;

    @Resource
    private RedisCache redisCache;

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
    @Transactional
    @Override
    public int insertEpidemicAccessRecord(EpidemicAccessRecord epidemicAccessRecord) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 设置报备账号
        epidemicAccessRecord.setUserId(user.getUserId());
        epidemicAccessRecord.setUsername(user.getUserName());
        epidemicAccessRecord.setCreateTime(DateUtils.getNowDate());
        epidemicAccessRecord.setCreateBy(SecurityUtils.getUsername());
        epidemicAccessRecord.setRecordId(IdGenerator.generatorId());
        // 如果是进入社区，添加隔离记录
        if (epidemicAccessRecord.getAccessType().equals(AccessTypeEnum.IN.getCode())) {
            EpidemicIsolationRecord record = new EpidemicIsolationRecord();
            record.setUserId(user.getUserId());
            record.setUsername(user.getUserName());
            record.setRealName(epidemicAccessRecord.getRealName());
            record.setTelephone(epidemicAccessRecord.getTelephone());
            record.setDelFlag("0");
            // 设置隔离时长与隔离结束时间
            EpidemicIsolationPolicy policy = epidemicIsolationPolicyService.getById(1);
            Integer isolationDay = policy.getIsolationDay();
            record.setIsolationTime(isolationDay);
            // 设置隔离结束时间：当前时间 + 隔离政策天数
            LocalDateTime localDateTime = LocalDateTime.now().plusDays(policy.getIsolationDay());
            ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
            record.setIsolationFinishTime(Date.from(zdt.toInstant()));
            record.setCreateTime(new Date());
            record.setCreateBy(user.getUserName());
            epidemicIsolationRecordService.save(record);
            // 添加到redis做每日自减操作
            Map<String, Integer> map = new HashMap<>();
            map.put(record.getRecordId().toString(), record.getIsolationTime());
            redisCache.setCacheMap(CacheConstants.EPIDEMIC_ISOLATION_TIME, map);
        }
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
        epidemicAccessRecord.setUpdateBy(SecurityUtils.getUsername());
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
