package com.yufeng.rabbit.mq.routing;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RoutingConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "directs"),
                    key = {"info", "error"}
            )
    })
    public void receive1(String message) {
        System.out.println("message1 = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "directs"),
                    key = {"info"}
            )
    })
    public void receive2(String message) {
        System.out.println("message2 = " + message);
    }

}
