package com.wenjing.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wenjing.rabbitmq.utils.RabbitConstraint;
import com.wenjing.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class PublisherTopic {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        Map<String, String> weatherMap = new HashMap<>();
        weatherMap.put("Ireland.Dublin.20220821", "the weather of Dublin");
        weatherMap.put("US.Chicago.20220821", "the weather of Chicago");
        weatherMap.put("US.Chicago.20220822", "the weather of Chicago");
        weatherMap.put("US.Houston.20220821", "the weather of Houston");
        weatherMap.put("UK.London.20220822", "the weather of London");
        weatherMap.put("Japan.Tokyo.20220822", "the weather of Tokyo");
        Iterator<Map.Entry<String, String>> iterator = weatherMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            channel.basicPublish(RabbitConstraint.EXCHANGE_WEATHER_TOPIC, entry.getKey(), null, entry.getValue().getBytes());
        }
        channel.close();
        connection.close();
    }
}
