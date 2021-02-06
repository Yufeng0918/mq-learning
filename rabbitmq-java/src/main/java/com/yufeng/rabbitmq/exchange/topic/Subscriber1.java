package com.yufeng.rabbitmq.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.yufeng.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;

public class Subscriber1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("topics", "topic");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "topics", "user.*");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });

    }
}
