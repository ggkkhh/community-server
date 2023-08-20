package com.roydon.business.epidemic.task;

import com.roydon.business.epidemic.domain.entity.EpidemicIsolationRecord;
import com.roydon.business.epidemic.service.IEpidemicIsolationRecordService;
import com.roydon.common.constant.CacheConstants;
import com.roydon.common.core.redis.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author roydon
 * @date 2023-08-09 7:22【星期三】
 * @description com.roydon.business.epidemic.task
 * <p> 隔离记录定时任务 </p>
 **/
@Slf4j
@Component
public class IsolationRecordTask {

    @Resource
    private RedisCache redisCache;

    @Resource
    private IEpidemicIsolationRecordService epidemicIsolationRecordService;

    /**
     * 定时更新隔离时间，隔离时间减一
     */
    public void updateIsolationTime() {
        log.info("开始从redis更新隔离天数==>");
        // 获取redis中的浏览量
        Map<String, Integer> isolationMap = redisCache.getCacheMap(CacheConstants.EPIDEMIC_ISOLATION_TIME);
        List<EpidemicIsolationRecord> recordList = isolationMap.entrySet().stream().map(entry -> {
            if (entry.getValue() > 0) {
                EpidemicIsolationRecord an = new EpidemicIsolationRecord();
                an.setRecordId(Long.parseLong(entry.getKey()));
                // redis中自减一
                epidemicIsolationRecordService.decreaseIsolationTime(Long.parseLong(entry.getKey()));
                Integer isolationTime = entry.getValue();
                an.setIsolationTime(--isolationTime);
                return an;
            }
            return null;
        }).collect(Collectors.toList());
        // 更新数据库
        epidemicIsolationRecordService.updateBatchById(recordList);
        log.info("<==隔离天数数据库与redis同步成功");
    }

}
