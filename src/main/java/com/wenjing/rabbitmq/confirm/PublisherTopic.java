package com.wenjing.rabbitmq.confirm;

import com.rabbitmq.client.*;
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


        //******************************************************
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("message has been accepted by broker : " + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {

            }
        });

        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return returnMessage) {
                System.err.println("===============");
                System.err.println(returnMessage.getReplyCode() + "---------" + returnMessage.getReplyText());
                System.err.println(returnMessage.getExchange() + "---------" + returnMessage.getRoutingKey());
                System.err.println(returnMessage.getBody());
                System.err.println("===============");
            }
        });
        //******************************************************


        Iterator<Map.Entry<String, String>> iterator = weatherMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            channel.basicPublish(RabbitConstraint.EXCHANGE_WEATHER_TOPIC, entry.getKey(), true, null, entry.getValue().getBytes());
        }
//        channel.close();
//        connection.close();
    }
}
