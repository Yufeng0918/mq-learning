package com.yufeng.rabbitmq.direct;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.yufeng.rabbitmq.utils.RabbitMqUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DirectQueueSender {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {

        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        RabbitMqUtils.closeConnectionAndChannle(channel, connection);
    }
}
