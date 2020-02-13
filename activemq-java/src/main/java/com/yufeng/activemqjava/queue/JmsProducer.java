package com.yufeng.activemqjava.queue;

import org.apache.activemq.*;

import javax.jms.*;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @Auther: daiyu
 * @Date: 31/1/20 14:39
 * @Description:
 */
public class JmsProducer {

    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616?jms.useAsyncSend=true";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory(ACTIVEMQ_URL);
        ((ActiveMQConnectionFactory)connectionFactory).setUseAsyncSend(true);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(destination);

        long delay = 3000;
        long period = 4000;
        int repeat = 5;

        IntStream.range(0, 3).forEach(i -> {
            try {
                TextMessage textMessage = session.createTextMessage("msg - " + i);
                textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
                textMessage.setJMSMessageID(UUID.randomUUID().toString().substring(0, 10));
                textMessage.setBooleanProperty("vip", Boolean.TRUE);
                String msgId = textMessage.getJMSMessageID();

//                textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
//                textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
//                textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);

                ((ActiveMQMessageProducer)messageProducer).send(textMessage, new AsyncCallback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("success " + msgId);
                    }

                    @Override
                    public void onException(JMSException exception) {
                        System.out.println("failed " + msgId);
                    }
                });

                //                MapMessage mapMessage = session.createMapMessage();
                //                mapMessage.setString("K1", "V1");
                //                messageProducer.send(textMessage);
                //                messageProducer.send(mapMessage);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });


        session.close();
        connection.close();
    }
}
