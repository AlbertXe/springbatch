package com.rabbitmq.demo3;

import com.rabbitmq.client.*;
import com.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * HASEE
 * 2019/7/31 20:48
 */
public class Receiver1 {
    private static String exchange = "testexchange";

    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("queue1", false, false, false, null);
        channel.queueBind("queue1", exchange, "");

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1"+new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("queue1", false, consumer);
    }
}
