package com.yufeng.rabbit.mq.hello;


import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue("hello"))
public class HelloConsumer {

    @RabbitHandler
    public void receive1(String message) {
        System.out.println("message = " + message);
    }
}
