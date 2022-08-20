package com.wenjing.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wenjing.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static final String QUEUE_NAME = "helloWorld";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "hello rabbitMQ";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("Message :--- " + msg + " ---, has been delivered");
        channel.close();
        connection.close();
    }
}
