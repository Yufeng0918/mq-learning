package com.yufeng.rabbitmq.worker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.yufeng.rabbitmq.utils.RabbitMqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.stream.IntStream;

@Slf4j
public class WorkerQueueSender {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);
        String message = "Hello work queue";
        IntStream.range(0, 10).forEach(i -> {
            try {
                channel.basicPublish("", "work", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println(" [x] Sent '" + message + "'");
        RabbitMqUtils.closeConnectionAndChannle(channel, connection);
    }
}
