package com.rabbitmq.spring;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

/**
 * HASEE
 * 2019/7/31 22:17
 */
@Configuration
public class ProductConfig {
    public Queue queue() {
        return new Queue("queue");
    }
}
