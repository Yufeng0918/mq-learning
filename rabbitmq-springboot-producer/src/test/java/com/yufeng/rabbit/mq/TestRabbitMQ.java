package com.yufeng.rabbit.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest(classes = RabbitmqSpringbootProducerApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void delay() {
        this.rabbitTemplate.convertAndSend(
                "delay_exchange",
                "contract.delay",
                "delay message",
                message -> {
                    message.getMessageProperties().setExpiration("6000");
                    return message;
                }
        );
        System.out.println(LocalDateTime.now());
    }

    @Test
    public void route() {
        rabbitTemplate.convertAndSend("directs", "error", "error object");
        rabbitTemplate.convertAndSend("directs", "info", "info object");
    }

    @Test
    public void fanout() {
        rabbitTemplate.convertAndSend("logs", "", "fanout object");
    }

    @Test
    public void testDirect() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }

    @Test
    public void testWorker() {
        IntStream.range(0, 10).forEach(i ->
                rabbitTemplate.convertAndSend("work", "hello worker" + i)
        );
    }
}
