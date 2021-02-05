package com.yufeng.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RabbitMqUtils {

    private static ConnectionFactory factory;

    static {
        factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/ems");
        factory.setUsername("ems");
        factory.setPassword("ems");

    }

    public static Connection getConnection() {

        Connection connection = null;
        try {

            connection = factory.newConnection();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return connection;
    }

    public static void closeConnectionAndChannle(Channel channel, Connection connection) {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
