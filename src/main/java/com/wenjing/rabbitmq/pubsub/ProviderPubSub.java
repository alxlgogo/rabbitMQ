package com.wenjing.rabbitmq.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wenjing.rabbitmq.utils.RabbitConstraint;
import com.wenjing.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Youtuber, subscriber.
 * Weather forecast.
 */
public class ProviderPubSub {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //publisher only need to blind with exchange
        channel.basicPublish(RabbitConstraint.EXCHANGE_WEATHER, "", null, "weather msgs".getBytes());
        channel.close();
        connection.close();
    }
}
