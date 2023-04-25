package com.roydon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Application
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/25
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
