package com.yufeng.rabbit.mq;


import com.yufeng.rabbit.config.QueueConfig;
import com.yufeng.rabbit.RabbitmqSpringbootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@SpringBootTest(classes = RabbitmqSpringbootApplication.class)
@RunWith(SpringRunner.class)
public class TestExchange {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private QueueConfig queueConfig;

    @Test
    public void testDelayQueuePerMessageTTL() throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            long expiration = i * 1000;
//            rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
//                    "Message From delay_queue_per_message_ttl with expiration " + expiration, new ExpirationMessagePostProcessor(expiration));

            this.rabbitTemplate.convertAndSend(
                    QueueConfig.DELAY_EXCHANGE_NAME,
                    QueueConfig.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
                    "delay message delay " + expiration,
                    message -> {
                        message.getMessageProperties().setExpiration(String.valueOf(expiration));
                        return message;
                    }
            );
        }
    }

    @Test
    public void testDelayQueuePerQueueTTL() throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            long expiration = i * 2 * 1000;
//            rabbitTemplate.convertAndSend(QueueConfig.DELAY_QUEUE_PER_MESSAGE_TTL_NAME,
//                    "Message From delay_queue_per_message_ttl with expiration " + expiration, new ExpirationMessagePostProcessor(expiration));
            System.out.println("send message " + LocalDateTime.now());
            this.rabbitTemplate.convertAndSend(
                    QueueConfig.DELAY_EXCHANGE_NAME,
                    QueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME,
                    "delay message delay " + expiration,
                    message -> {
                        message.getMessageProperties().setExpiration(String.valueOf(expiration));
                        return message;
                    }
            );
        }
    }
}
