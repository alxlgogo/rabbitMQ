package com.wenjing.rabbitmq.workqueue;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wenjing.rabbitmq.utils.RabbitConstraint;
import com.wenjing.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Work Queue:
 * Applicable to ticketing system, sending SMS business.
 */
public class ProviderWQ {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstraint.QUEUE_SMS, false, false, false, null);
        for (int i = 1; i <= 100; i++) {
            SMS sms = new SMS("customer" + i, " 083204xx" + i, " The ticket purchase was successful.");
            String jsonString = new Gson().toJson(sms);
            channel.basicPublish("", RabbitConstraint.QUEUE_SMS, null, jsonString.getBytes());
        }
        System.out.println("dada has been send successfully");
        channel.close();
        connection.close();
    }
}
