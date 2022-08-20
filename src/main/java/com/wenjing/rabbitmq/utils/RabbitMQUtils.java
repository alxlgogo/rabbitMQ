package com.wenjing.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    private static Connection connection;

    static {
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/test");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = connectionFactory.newConnection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
