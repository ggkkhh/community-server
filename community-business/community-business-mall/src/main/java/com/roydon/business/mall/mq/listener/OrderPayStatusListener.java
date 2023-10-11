package com.roydon.business.mall.mq.listener;

import com.roydon.business.mall.domain.entity.MallOrder;
import com.roydon.business.mall.enums.OrderPayStatus;
import com.roydon.business.mall.mq.config.OrderDelayedMessageConfig;
import com.roydon.business.mall.service.IMallOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OrderPayStatusListener
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/11
 **/
@Slf4j
@Component
public class OrderPayStatusListener {

    @Resource
    private IMallOrderService orderService;

    /**
     * 延时消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = OrderDelayedMessageConfig.DIRECT_QUEUE, durable = "true"),
            exchange = @Exchange(name = OrderDelayedMessageConfig.DELAYED_EXCHANGE, delayed = "true"),
            key = OrderDelayedMessageConfig.ROUTING_KEY
    ))
    public void listenDelayMessage(String orderId) {
        log.info("接收到处理订单支付状态的延迟消息：{}", orderId);
        MallOrder order = orderService.getById(orderId);
        if (order.getPayStatus().equals(OrderPayStatus.NO_PAY.getCode())) {
            // 未支付，取消订单
            orderService.cancelOrder(orderId);
        }
    }

}
