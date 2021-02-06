package com.yufeng.rabbitmq.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yufeng.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Publisher {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs_direct", "direct");
        channel.basicPublish("logs_direct", "info", null, "direct info1 message".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish("logs_direct", "info", null, "direct info2 message".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish("logs_direct", "error", null, "direct error1 message".getBytes(StandardCharsets.UTF_8));
        channel.basicPublish("logs_direct", "error", null, "direct error2 message".getBytes(StandardCharsets.UTF_8));
        RabbitMqUtils.closeConnectionAndChannle(channel, connection);
    }
}
