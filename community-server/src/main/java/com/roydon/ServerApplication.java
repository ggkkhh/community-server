package com.roydon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@MapperScan("com.roydon.**.mapper")
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    /**
     * 消息转换器：Jackson2JsonMessageConverter防止到mq乱码，原来使用的是jdk编码
     */
    @Bean
    public MessageConverter jacksonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setCreateMessageIds(true);
        return converter;
    }
}
