package com.rabbitmq.demo1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.util.ConnectionUtil;

/**
 * HASEE
 * 2019/7/29 22:42
 */
public class Receiver {
    public static void main(String[] args) throws Exception{


        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("testhello", false, false, false, null);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume("testhello", true, consumer );

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(message);
        }
    }
}
