package com.yufeng.activemqjava.topic;

import org.apache.activemq.ActiveMQSslConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Auther: daiyu
 * @Date: 31/1/20 15:56
 * @Description:
 */
public class JmsSubscriber {

    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException, IOException {

        ConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic destination = session.createTopic(TOPIC_NAME);
        MessageConsumer messageConsumer = session.createConsumer(destination);

        messageConsumer.setMessageListener(message -> {
            try {
                System.out.println(((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
