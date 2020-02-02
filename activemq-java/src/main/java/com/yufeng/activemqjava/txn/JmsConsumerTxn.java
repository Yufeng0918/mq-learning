package com.yufeng.activemqjava.txn;

import org.apache.activemq.ActiveMQSslConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Auther: daiyu
 * @Date: 31/1/20 15:56
 * @Description:
 */
public class JmsConsumerTxn {

    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {

        ConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(destination);

        while (true) {

            TextMessage message = (TextMessage) messageConsumer.receive(4000);
            if (null != message) {
                System.out.println("Received: " + message.getText());
                message.acknowledge();
            } else {
                break;
            }
        }

        session.commit();
        session.close();
        connection.close();
    }
}
