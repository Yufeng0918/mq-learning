package com.yufeng.activemqspringbootconsumer.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

/**
 * @Auther: daiyu
 * @Date: 12/2/20 13:15
 * @Description:
 */

@Service
public class QueueConsumer {

//    @JmsListener(destination = "${myqueue}")
    public void receiveQueueMsg(TextMessage message) throws Exception{
        System.out.println("Received: " + message.getText());
    }


    @JmsListener(destination = "${mytopic}")
    public void receiveTopicMsg(TextMessage message) throws Exception{
        System.out.println("Received: " + message.getText());
    }
}
