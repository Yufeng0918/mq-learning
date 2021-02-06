package com.yufeng.rabbitmq.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yufeng.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Publisher {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("topics", "topic");
        channel.basicPublish("topics", "user.save", null, "user save message".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish("topics", "user.delete", null, "user delete message".getBytes(StandardCharsets.UTF_8));
        RabbitMqUtils.closeConnectionAndChannle(channel, connection);
    }
}
