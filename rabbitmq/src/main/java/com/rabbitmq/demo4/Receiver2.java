package com.rabbitmq.demo4;

import com.rabbitmq.client.*;
import com.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * HASEE
 * 2019/7/31 20:48
 */
public class Receiver2 {
    private static String exchange = "testroute";

    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("queue2", false, false, false, null);
        channel.queueBind("queue2", exchange, "key1");
        //如果执行多个标记 路由
        channel.queueBind("queue2", exchange, "key3");

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2"+new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("queue2", false, consumer);
    }
}
