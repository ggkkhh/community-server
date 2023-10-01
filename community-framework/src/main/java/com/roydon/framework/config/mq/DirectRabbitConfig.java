package com.roydon.framework.config.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {

    public static final String DIRECT_EXCHANGE_NAME = "DirectExchange";
    public static final String DIRECT_QUEUE_NAME = "DirectQueue";
    public static final String DIRECT_ROUTING_NAME = "DirectRouting";

    //Direct交换机
    @Bean
    DirectExchange DirectExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }

    //队列
    @Bean
    public Queue DirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);

        QueueBuilder.durable(DIRECT_QUEUE_NAME).ttl(10000) //超时时间
                .deadLetterExchange("dl.direct") //超时后进入死信交换机dl.direct
                .build();
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(DIRECT_QUEUE_NAME, true, false, false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：DirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(DirectQueue()).to(DirectExchange()).with(DIRECT_ROUTING_NAME);
    }

    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

}
