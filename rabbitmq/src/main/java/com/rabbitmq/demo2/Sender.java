package com.rabbitmq.demo2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.util.ConnectionUtil;

/**
 * HASEE
 * 2019/7/29 22:35
 */
public class Sender {

    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //队列名 是否持久化队列 默认内存中，true就保存erl数据库
        // 链接关闭是否自动删除队列
        // 是否私有当前队列
        channel.queueDeclare("testwork", false, false, false, null);
        for (int i = 0; i < 100; i++) {
            channel.basicPublish("", "testwork", null, ("发送的消息"+i).getBytes());

        }
        channel.close();
        connection.close();


    }
}
