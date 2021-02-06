package com.yufeng.rabbit.mq.delay;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class DelayConsumer {


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "delay_queue2"),
                    exchange = @Exchange(value = "delay_exchange", type = "direct"),
                    key = {"contract.direct"}
            )
    })
    public void receive1(String msg, Message message, Channel channel) throws IOException {
        log.info("===============接收队列接收消息====================");
        log.info("接收时间:{},接受内容:{}", LocalDateTime.now(), msg);
        //通知 MQ 消息已被接收,可以ACK(从队列中删除)了
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        try {

        } catch (Exception e) {
            log.error("============消费失败,尝试消息补发再次消费!==============");
            log.error(e.getMessage());
            /**
             * basicRecover方法是进行补发操作，
             * 其中的参数如果为true是把消息退回到queue但是有可能被其它的consumer(集群)接收到，
             * 设置为false是只补发给当前的consumer
             */
            channel.basicRecover(false);
        }
    }
}
