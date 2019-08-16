package com.rabbitmq.demo5;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.util.ConnectionUtil;

/**
 * topic 模式  通配符 * 一个 #多个
 * HASEE
 * 2019/7/31 21:16
 */
public class Sender {
    private static String exchange = "testtopic";

    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchange, "topic");
        channel.basicPublish(exchange, "abc.1.2", null, "topic模式".getBytes());
        channel.close();
        connection.close();


    }
}
