# ActiveMQ

## Overview
#### MQ is message queue
+ API of produce or consume message
+ MQ HA, cluster and error tolerance
+ MQ persist
+ Delay or Schedule task
+ Ack
+ Spring Integeration
+ Development language
#### ActiveMQ, message-oritented middleware
+ Implementation of Java Message Service, e.g. Theory is flying, implmentation is walking on ground
+ Features
    + decouple downstream/upstream application
        + upstream publish message to topic/queue 
        + downstream subscribe/consume interest topic/queue 
    + async communication
        + producer send message async
        + consumer consume message async
    + reduce peak flow request to application
        + MQ receive flowing message
        + application consume message based on its own capabilities
***
   
    
## ActiveMQ in action
#### Commands
- ./activemq start
- ./activemq stop
#### Config
- port: 61616
- console: localhost:8161, e.g. admin/admin
#### Java
- ConnectionFactory
    Connection
        Session to create message
- Message contains session to send/receive message to Destination
- Destination
    + created by session
    + Queue: point-to-point, sender & receiver
        + one message only consume by one receiver
        + message will be consume till receiver to receive message
    + Topic: publish-and-subscribe, publisher & subscriber
        + one message will be sent to all subscriber
        + message will be thrown away if there is no subscriber
        + may slow down if huge subscriber due to message copy
- Producer
    + send(Message)
- Consumer
    + receive(): block IO
    + receive(timeout): wait for timeout
    + setMessageListener(message -> {}): messageListener to listen the message
```
ConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory(ACTIVEMQ_URL);
Connection connection = connectionFactory.createConnection();
connection.start();

Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
Queue destination = session.createQueue(QUEUE_NAME);

MessageProducer messageProducer = session.createProducer(destination);
MessageConsumer messageConsumer = session.createConsumer(destination);
```
***


## JMS
#### J2EE 
- 13 standard of enterprise development
- JDBC
- JNDI
- EJB/RMI
- JSP/Servlet
- JMS, Java Message Service, async message oriented API
- JTA
- JavaMail
#### JMS Product
- ActiveMQ
    + QPS, 10,000
    + master-slave
- RocketMQ
    + QPS, n0,000
    + master-slave
- Kafka
    + QPS, 100,000
    + distributed
    + may lose message or duplicated message
#### JMS Component
- Producer
- Consumer
- JMS Provider: MQ middleware
- JMS Message