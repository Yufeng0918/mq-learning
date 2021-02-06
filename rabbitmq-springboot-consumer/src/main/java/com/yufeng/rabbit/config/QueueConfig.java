package com.yufeng.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    /**
     * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
     * 每个message可以控制自己的失效时间
     */
    public final static String DELAY_QUEUE_PER_MESSAGE_TTL_NAME = "delay_queue_per_message_ttl";

    /**
     * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
     * 队列里所有的message都有统一的失效时间
     */
    public final static String DELAY_QUEUE_PER_QUEUE_TTL_NAME = "delay_queue_per_queue_ttl";
    public final static int QUEUE_EXPIRATION = 40000;

    /**
     * message失效后进入的队列，也就是实际的消费队列
     */
    public final static String DELAY_PROCESS_QUEUE_NAME = "delay_process_queue";

    /**
     * DLX
     */
    public final static String DELAY_EXCHANGE_NAME = "delay_ex";

    /**
     * 路由到delay_queue_per_queue_ttl的exchange
     */
    final static String PER_QUEUE_TTL_EXCHANGE_NAME = "per_queue_ttl_exchange";

    /**
     * 创建DLX exchange
     *
     * @return
     */
    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    /**
     * 创建per_queue_ttl_exchange
     *
     * @return
     */
    @Bean
    DirectExchange perQueueTTLExchange() {
        return new DirectExchange(PER_QUEUE_TTL_EXCHANGE_NAME);
    }

    /**
     * 创建delay_queue_per_message_ttl队列
     *
     * @return
     */
    @Bean
    Queue delayQueuePerMessageTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
                .deadLetterExchange(DELAY_EXCHANGE_NAME)
                .deadLetterRoutingKey(DELAY_PROCESS_QUEUE_NAME)
                .build();
    }

    /**
     * 创建delay_queue_per_queue_ttl队列
     *
     * @return
     */
    @Bean
    Queue delayQueuePerQueueTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_PER_QUEUE_TTL_NAME)
                .deadLetterExchange(DELAY_EXCHANGE_NAME)
                .deadLetterRoutingKey(DELAY_PROCESS_QUEUE_NAME)
                .expires(QUEUE_EXPIRATION)
                .build();
    }

    /**
     * 创建delay_process_queue队列，也就是实际消费队列
     *
     * @return
     */
    @Bean
    Queue delayProcessQueue() {
        return QueueBuilder.durable(DELAY_PROCESS_QUEUE_NAME)
                .build();
    }

    /**
     * 将DLX绑定到实际消费队列
     *
     * @param delayProcessQueue
     * @param delayExchange
     * @return
     */
    @Bean
    Binding dlxBinding(Queue delayProcessQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayProcessQueue)
                .to(delayExchange)
                .with(DELAY_PROCESS_QUEUE_NAME);
    }

    /**
     * 将per_queue_ttl_exchange绑定到delay_queue_per_queue_ttl队列
     *
     * @param delayQueuePerQueueTTL
     * @param perQueueTTLExchange
     * @return
     */
    @Bean
    Binding queueTTLBinding(Queue delayQueuePerQueueTTL, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueuePerQueueTTL)
                .to(delayExchange)
                .with(DELAY_QUEUE_PER_QUEUE_TTL_NAME);
    }

    @Bean
    Binding messageTTLBinding(Queue delayQueuePerMessageTTL, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueuePerMessageTTL)
                .to(delayExchange)
                .with(DELAY_QUEUE_PER_MESSAGE_TTL_NAME);
    }
}