package com.roydon.business.mall.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * OrderDelayedMessageConfig
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/11
 * 订单延时队列交换机绑定
 **/
@Configuration
public class OrderDelayedMessageConfig {
    /**
     * 队列
     */
    public static final String DIRECT_QUEUE = "queue.order.payStatus";

    /**
     * 延迟交换机
     */
    public static final String DELAYED_EXCHANGE = "exchange.order.payStatus";

    /**
     * 绑定的routing key
     */
    public static final String ROUTING_KEY = "order.payStatus";

    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE,true);
    }

    /**
     * 交换机的类型为 x-delayed-message
     */
    @Bean
    public CustomExchange orderDelayedExchange() {
        Map<String,Object> map= new HashMap<>();
        map.put("x-delayed-type","direct");
        return new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",true,false,map);
    }

    @Bean
    public Binding delayOrderBinding() {
        return BindingBuilder.bind(directQueue()).to(orderDelayedExchange()).with(ROUTING_KEY).noargs();
    }

}
