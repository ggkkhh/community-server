//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
    public static void main(String[] args) throws InterruptedException {
        System.out.println(generatorId());
    }
}
