package com.yufeng.rabbit.consumer;

import com.rabbitmq.client.Channel;
import com.yufeng.rabbit.config.QueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author daiyu
 */
@Component
@Slf4j
public class DelayConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "delay_process_queue"),
                    exchange = @Exchange(value = "delay_ex"),
                    key = {"delay_process_queue"}
            )
    })
    public void receive1(String msg, Message message, Channel channel) throws IOException {
        log.info("receive1 接收时间:{},接受内容:{}", LocalDateTime.now(), msg);
        //通知 MQ 消息已被接收,可以ACK(从队列中删除)了
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        try {

        } catch (Exception e) {
            log.error("============消费失败,尝试消息补发再次消费!==============");
            log.error(e.getMessage());
            channel.basicRecover(false);
        }
    }


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "delay_process_queue"),
                    exchange = @Exchange(value = "delay_ex"),
                    key = {"delay_process_queue"}
            )
    })
    public void receive2(String msg, Message message, Channel channel) throws IOException {
        log.info("receive2 接收时间:{},接受内容:{}", LocalDateTime.now(), msg);
        //通知 MQ 消息已被接收,可以ACK(从队列中删除)了
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        try {

        } catch (Exception e) {
            log.error("============消费失败,尝试消息补发再次消费!==============");
            log.error(e.getMessage());
            channel.basicRecover(false);
        }
    }
}
