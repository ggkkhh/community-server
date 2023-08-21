package com.roydon.business.epidemic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.epidemic.domain.entity.EpidemicIsolationRecord;
import com.roydon.business.epidemic.mapper.EpidemicIsolationRecordMapper;
import com.roydon.business.epidemic.service.IEpidemicIsolationRecordService;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.constant.UserConstants;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.core.redis.RedisCache;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.StringUtils;
import com.roydon.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 隔离记录Service业务层处理
 *
 * @author roydon
 * @date 2023-08-09
 */
@Slf4j
@Service
public class EpidemicIsolationRecordServiceImpl extends ServiceImpl<EpidemicIsolationRecordMapper, EpidemicIsolationRecord> implements IEpidemicIsolationRecordService {

    @Resource
    private EpidemicIsolationRecordMapper epidemicIsolationRecordMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private RedisCache redisCache;

    @Async
    @PostConstruct
    public void init() {
        log.info("隔离记录隔离天数写入缓存开始==>");
        LambdaQueryWrapper<EpidemicIsolationRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(EpidemicIsolationRecord::getRecordId, EpidemicIsolationRecord::getIsolationTime);
        queryWrapper.gt(EpidemicIsolationRecord::getIsolationTime, 0);
        List<EpidemicIsolationRecord> recordList = list(queryWrapper);
        Map<String, Integer> recordMap = new HashMap<>();
        recordList.forEach(r -> {
            Long recordId = r.getRecordId();
            Integer isolationTime = r.getIsolationTime();
            recordMap.put(recordId.toString(), isolationTime);
        });
        redisCache.setCacheMap(CacheConstants.EPIDEMIC_ISOLATION_TIME, recordMap);
        log.info("<==隔离记录隔离天数写入缓存成功");
    }

    /**
     * 查询隔离记录
     *
     * @param recordId 隔离记录主键
     * @return 隔离记录
     */
    @Override
    public EpidemicIsolationRecord selectEpidemicIsolationRecordByRecordId(Long recordId) {
        return epidemicIsolationRecordMapper.selectEpidemicIsolationRecordByRecordId(recordId);
    }

    /**
     * 查询隔离记录列表
     *
     * @param epidemicIsolationRecord 隔离记录
     * @return 隔离记录
     */
    @Override
    public List<EpidemicIsolationRecord> selectEpidemicIsolationRecordList(EpidemicIsolationRecord epidemicIsolationRecord) {
        return epidemicIsolationRecordMapper.selectEpidemicIsolationRecordList(epidemicIsolationRecord);
    }

    /**
     * 新增隔离记录
     *
     * @param epidemicIsolationRecord 隔离记录
     */
    @Override
    public void insertEpidemicIsolationRecord(EpidemicIsolationRecord epidemicIsolationRecord) {
        epidemicIsolationRecord.setCreateTime(DateUtils.getNowDate());
        String username = epidemicIsolationRecord.getUsername();
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户账号必须填写");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        if (UserConstants.UNIQUE.equals(userService.checkUserNameUnique(sysUser))) {
            // 表中[无]此用户账号
            throw new UsernameNotFoundException("此用户不存在，请检查账号是否输入正确，或先注册");
        }
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, username);
        SysUser oneUser = userService.getOne(queryWrapper);
        epidemicIsolationRecord.setUserId(oneUser.getUserId());
        epidemicIsolationRecord.setUsername(oneUser.getUserName());
        // 设置隔离结束时间：当前时间+隔离时间
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(epidemicIsolationRecord.getIsolationTime());
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        epidemicIsolationRecord.setIsolationFinishTime(Date.from(zdt.toInstant()));
//        int i = epidemicIsolationRecordMapper.insertEpidemicIsolationRecord(epidemicIsolationRecord);
        saveOrUpdate(epidemicIsolationRecord);
        // 添加到redis
        Map<String, Integer> map = new HashMap<>();
        map.put(epidemicIsolationRecord.getRecordId().toString(), epidemicIsolationRecord.getIsolationTime());
        redisCache.setCacheMap(CacheConstants.EPIDEMIC_ISOLATION_TIME, map);
    }

    /**
     * 修改隔离记录
     *
     * @param epidemicIsolationRecord 隔离记录
     * @return 结果
     */
    @Override
    public int updateEpidemicIsolationRecord(EpidemicIsolationRecord epidemicIsolationRecord) {
        epidemicIsolationRecord.setUpdateTime(DateUtils.getNowDate());
        return epidemicIsolationRecordMapper.updateEpidemicIsolationRecord(epidemicIsolationRecord);
    }

    /**
     * 隔离时间减一业务
     *
     * @param recordId id
     */
    @Override
    public void decreaseIsolationTime(Long recordId) {
        // 缓存自减一
        redisCache.incrementCacheMapValue(CacheConstants.EPIDEMIC_ISOLATION_TIME, recordId.toString(), -1);
    }

    /**
     * 批量删除隔离记录
     *
     * @param recordIds 需要删除的隔离记录主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicIsolationRecordByRecordIds(Long[] recordIds) {
        int i = epidemicIsolationRecordMapper.deleteEpidemicIsolationRecordByRecordIds(recordIds);
        if (i > 0) {
            // 删除redis缓存
            for (Long record : recordIds) {
                redisCache.deleteCacheMapValue(CacheConstants.EPIDEMIC_ISOLATION_TIME, record.toString());
            }
        }
        return i;
    }

    /**
     * 删除隔离记录信息
     *
     * @param recordId 隔离记录主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicIsolationRecordByRecordId(Long recordId) {
        redisCache.deleteCacheMapValue(CacheConstants.EPIDEMIC_ISOLATION_TIME, recordId.toString());
        return epidemicIsolationRecordMapper.deleteEpidemicIsolationRecordByRecordId(recordId);
    }
}
