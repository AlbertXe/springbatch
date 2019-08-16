package com.rabbitmq.demo2;

import com.rabbitmq.client.*;
import com.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * HASEE
 * 2019/7/29 22:42
 */
public class Receiver2 {
    public static void main(String[] args) throws Exception{


        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("testwork", false, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2 收到的消息" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);//false表示接受到消息
                super.handleDelivery(consumerTag, envelope, properties, body);
            }
        };

        channel.basicConsume("testwork", false, consumer );
    }
}
