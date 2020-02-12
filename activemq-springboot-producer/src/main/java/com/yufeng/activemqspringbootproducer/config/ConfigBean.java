package com.yufeng.activemqspringbootproducer.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @Auther: daiyu
 * @Date: 6/2/20 23:41
 * @Description:
 */

@Component
@EnableJms
public class ConfigBean {

    @Value("${myqueue}")
    private String myqueue;

    @Value("${mytopic}")
    private String myTopic;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(myqueue);
    }

    @Bean
    private Topic topic() {return new ActiveMQTopic(myTopic);}
}
