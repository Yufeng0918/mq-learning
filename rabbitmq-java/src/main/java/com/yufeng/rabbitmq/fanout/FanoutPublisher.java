package com.yufeng.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.yufeng.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FanoutPublisher {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs", "fanout");
        String message = "Hello World!";
        channel.basicPublish("logs", "", null, "fanout message".getBytes(StandardCharsets.UTF_8));
        RabbitMqUtils.closeConnectionAndChannle(channel, connection);
    }
}
