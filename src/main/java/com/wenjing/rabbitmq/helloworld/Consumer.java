package com.wenjing.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wenjing.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class Consumer {
    private static final String QUEUE_NAME = "helloWorld";

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME, new MyConsumer(channel));
    }
}


