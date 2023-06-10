package com.roydon.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskCenterUtil {

    public static Integer CORE_POOL_SIZE = 10;
    public static Integer MAX_NUM_POOL_SIZE = 10;
    public static Integer MAX_MESSAGE_SIZE = 100;
    public static Long KEEP_ALIVE_TIME = 60L;

    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_NUM_POOL_SIZE, KEEP_ALIVE_TIME,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(MAX_MESSAGE_SIZE), new ThreadPoolExecutor.CallerRunsPolicy());


    private TaskCenterUtil() {
    }

    private static TaskCenterUtil taskCenterUtil = new TaskCenterUtil();

    public static TaskCenterUtil getTaskCenterUtil() {
        return taskCenterUtil;
    }

    public void submitTask(Callable task) {
        poolExecutor.submit(task);
    }

}
