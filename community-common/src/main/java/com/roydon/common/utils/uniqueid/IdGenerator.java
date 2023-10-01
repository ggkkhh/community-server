package com.roydon.common.utils.uniqueid;

import com.roydon.common.utils.uuid.IdUtils;

public class IdGenerator {

    public IdGenerator() {
    }

    public static String generatorId() {
        long id = SnowflakeIdWorker.getInstance().nextId();
        String uuid = IdUtils.simpleUUID();
        return String.format("%d%s", id, uuid);
    }

    public static String generatorShortId() {
        long id = SnowflakeIdWorker.getInstance().nextId();
        String uuid = IdUtils.shortUUID();
        return String.format("%d%s", id, uuid);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(generatorId());
    }
}
