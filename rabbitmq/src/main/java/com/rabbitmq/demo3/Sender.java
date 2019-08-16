package com.rabbitmq.demo3;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.util.ConnectionUtil;

/**
 * 发布订阅模式
 * HASEE
 * 2019/7/31 20:42
 */
public class Sender {
    private static String exchange = "testexchange";
    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT);//fanout 发布订阅
        channel.basicPublish(exchange, "", null, "发布订阅模式".getBytes());
        //消息发送到交换机，交换机没有保存功能，如果没有消费者，消息消失
        channel.close();
        connection.close();

    }
}
