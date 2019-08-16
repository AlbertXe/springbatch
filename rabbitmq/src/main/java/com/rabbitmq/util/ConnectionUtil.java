package com.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * HASEE
 * 2019/7/29 22:31
 */
public class ConnectionUtil {
    public static Connection getConnection() throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        connectionFactory.setVirtualHost("/test");
        return connectionFactory.newConnection();
    }
}
