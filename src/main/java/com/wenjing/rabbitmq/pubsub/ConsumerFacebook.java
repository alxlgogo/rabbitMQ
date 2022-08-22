package com.wenjing.rabbitmq.pubsub;

import com.rabbitmq.client.*;
import com.wenjing.rabbitmq.utils.RabbitConstraint;
import com.wenjing.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

public class ConsumerFacebook {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstraint.QUEUE_FACEBOOK, false, false, false, null);
        channel.queueBind(RabbitConstraint.QUEUE_FACEBOOK, RabbitConstraint.EXCHANGE_WEATHER, "");
        channel.basicQos(1);
        channel.basicConsume(RabbitConstraint.QUEUE_FACEBOOK, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Facebook " + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }
}
