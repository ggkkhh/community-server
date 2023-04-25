package com.roydon.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @USER: roydon
 * @DATE: 2023/4/25 16:39
 * @Description app
 **/
@Slf4j
@SpringBootApplication
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        log.info("app接口启动成功");
    }
}
