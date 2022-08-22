package com.wenjing.rabbitmq.pubsub;

import com.rabbitmq.client.*;
import com.wenjing.rabbitmq.utils.RabbitConstraint;
import com.wenjing.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerTwitter {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstraint.QUEUE_TWITTER, false, false, false, null);
        channel.queueBind(RabbitConstraint.QUEUE_TWITTER, RabbitConstraint.EXCHANGE_WEATHER, "");
        channel.basicQos(1);
        channel.basicConsume(RabbitConstraint.QUEUE_TWITTER, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Twitter " + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
