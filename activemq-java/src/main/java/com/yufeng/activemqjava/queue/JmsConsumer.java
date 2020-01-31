package com.yufeng.activemqjava.queue;

import org.apache.activemq.ActiveMQSslConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.stream.IntStream;

/**
 * @Auther: daiyu
 * @Date: 31/1/20 15:56
 * @Description:
 */
public class JmsConsumer {

    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {

        ConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(destination);

//        while (true) {
//            TextMessage message = (TextMessage) messageConsumer.receive(4000);
//            System.out.println("Received: " + message.getText());
//        }

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
