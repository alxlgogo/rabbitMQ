package com.wenjing.rabbitmq.workqueue;

import com.rabbitmq.client.*;
import com.wenjing.rabbitmq.utils.RabbitConstraint;
import com.wenjing.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * Multiple consumer
 */
public class ConsumerWQ1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstraint.QUEUE_SMS, false, false, false, null);
        //After the consumer processes a message and confirms it, it obtains a new message from the message queue.
        channel.basicQos(1);
        channel.basicConsume(RabbitConstraint.QUEUE_SMS, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonSMS = new String(body);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("WQ1  " + jsonSMS);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
