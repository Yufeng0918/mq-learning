package com.yufeng.activemqspringbootproducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;
import java.util.UUID;

/**
 * @Auther: daiyu
 * @Date: 6/2/20 23:44
 * @Description:
 */

@Component
public class QueueProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    public void produceMsg() {
        jmsMessagingTemplate.convertAndSend(queue, "uuid: " + UUID.randomUUID().toString().substring(0, 8));
    }

    @Scheduled(fixedDelay = 3000)
    public void produceMsgSchedule() {
        jmsMessagingTemplate.convertAndSend(topic, "schedule -> uuid: " + UUID.randomUUID().toString().substring(0, 8));
    }

    @Scheduled(fixedDelay = 3000)
    public void publishMsgSchedule() {
        jmsMessagingTemplate.convertAndSend(topic, "schedule -> uuid: " + UUID.randomUUID().toString().substring(0, 8));
    }
}
