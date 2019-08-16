package com.rabbitmq.demo4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.util.ConnectionUtil;

/**
 * 路由模式
 * HASEE
 * 2019/7/31 21:03
 */
public class Sender {
    private static String exchange = "testroute";

    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchange, "direct");//路由格式
        channel.basicPublish(exchange, "key3", null, "路由消息".getBytes());
        channel.close();
        connection.close();

    }
}
