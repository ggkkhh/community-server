package com.roydon.quartz.task;

import com.roydon.business.epidemic.task.IsolationRecordTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author roydon
 * @date 2023-08-09 7:21【星期三】
 * @description com.roydon.quartz.task
 * <p> 隔离记录定时任务 </p>
 **/
@Component("epidemicIsolationRecordTask")
public class EpidemicIsolationRecordTask {

    @Resource
    private IsolationRecordTask isolationRecordTask;

    /**
     * 隔离天数自减
     */
    public void decreaseIsolationTime() {
        isolationRecordTask.updateIsolationTime();
    }

}
