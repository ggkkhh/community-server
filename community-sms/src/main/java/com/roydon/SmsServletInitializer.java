package com.roydon;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * AppServletInitializer
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/27
 **/
public class SmsServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SmsServletInitializer.class);
    }
}
