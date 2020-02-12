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
- ./activemq start xbean:file:<filePath>/activemq01.xml
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
    + Header
        + JMS Destination
        + JMS DeliveryMode: persist and non-persist
        + JMS Expiration: default is never expire
        + JMS Priority: 0 - 9, 0-4 standard, 5-9 is urgent
        + JMS MessageID: universal Id created by JMS Provider
    + Properties
        + additional properties to enrich message metadata
    + Body
        + store message data
        + category
            + TextMessage: string
            + MapMessage: map object
            + BytesMessage
            + StreamMessage
            + ObjectMessage
- Message Reliable
    + Persist
        + Queue
            + setDeliveryMode(DeliveryMode.PERSISTENT | DeliveryMode.NON_PERSISTENT)
            + PERSISTENT is default delivery mode 
            + message will not be consume if MQ is down before consumer consume
        + Topic
            + subscriber 
                + connnection.setClientId()
                + TopicSubscriber receive() message
            + subsriber register as durable subscriber, then down offline
            + publisher publish message to subscriber
            + subscriber back online will receive the message published before
    + Transaction
        + Transaction
            + open transaction: connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            + commit transaction: session.commit();
        + Producer: commit the message to mq after transaction commit
        + Consumer: dequeue the message only after commit
    + Acknowledgement
        + Non-Transaction
            + AutoAck: default
            + ClientAck: client invoke message.acknowledge() to ack
            + DuplOkAct
        + Transaction
            + AutoAck once Consumer commit
            + NoAct if Consumer does not commit
***


## ActiveMQ Broker
- instance of activeMQ instance
- startup activeMQ inside java source code
```
BrokerService brokerService = new BrokerService();
brokerService.setUseJmx(true);
brokerService.addConnector("tcp://127.0.0.1:61616");
brokerService.start();
```
***

## Springboot Integeration
#### Config
```
spring:
  activemq:
    broker-url: tcp://127.0.0.1:61616
    user: admin
    password: admin
  jms:
    pub-sub-domain: false #false = queue, true = topic

myqueue: boot-activemq-queue
mytopic: boot-activemq-topic

@Component
@EnableJms
public class ConfigBean {

    @Value("${myqueue}")
    private String myqueue;
    @Value("${mytopic}")
    private String myTopic;

    @Bean
    public Queue queue() { return new ActiveMQQueue(myqueue);}
    @Bean
    private Topic topic() {return new ActiveMQTopic(myTopic);}
}
```
#### Producer
```
@Autowired
private JmsMessagingTemplate jmsMessagingTemplate;
    public void produceMsg() {
        jmsMessagingTemplate.convertAndSend(queue, "uuid: " + UUID.randomUUID().toString().substring(0, 8));
    }
}
```
#### Consumer Integration
```
@Service
public class QueueConsumer {

    @JmsListener(destination = "${myqueue|mytopic}")
    public void receive(TextMessage message) throws Exception{
        System.out.println("Received: " + message.getText());
    }
}
```
***


## Protocal
#### Support Protocal
- protocal type: ws, stomp, tcp, vm, mqtt, amqp e.g. NIO is recommended
- amqp: advanced message queueing protocal
- stop: stream text oriented protocal
#### TCP && NIO
- tcp
    + default protocal, listen to 61616
    + serialized via wire protocal
    + tcp://0.0.0.0:616161?key=value to set parameters
- nio
    + provider better performance, nio + tcp to support connection
    + nio to support different protocal via auto
    + auto+nio://0.0.0.0:61612
- config
    + OpenWire, STOMP, AMQP, and MQTT can be automatically detected
    + To configure ActiveMQ auto wire format detection over an NIO TCP connection use the auto+nio transport prefix
```
<transportConnector name="auto+nio" uri="auto+nio://0.0.0.0:61608?wireFormat.maxFrameSize=104857600"/>
```
***

## ActiveMQ Persist DataStorage
#### Overview
- message is persist even if activemq down
- message created by producer, persist to storage first, message deleted from database if consumer successfully consume the message
- activemq will check the offset of storage during the startup, re-send the un-delivery message
- data persist external
    + AMQ message store: persist message info file
    + Kaha DB after 5.3
    + JDBC + Juronal
    + Replicated LevelDB Store introduced after 5.9
#### Kaha DB
- file based persistence data, defualt storage machanism after 5.4
- contains transcation data log file and index file
    + db-<i>.log: transaction data store in log, old file achived into achive directory
    + db.data: B+ tree index file
    + db.redo: used to restore db.log and db.data if shutdown unexpectally
    + db.free: index for free space
    + lock: read shared lock and write exclusive lock
```
<persistenceAdapter>
    <kahaDB directory="${activemq.data}/kahadb"/>
</persistenceAdapter>
```
#### Replicated LevelDB
- file based persistence data, based on levelDB index after 5.8
- integrated with zookeeper
#### JDBC + AMQ Journal
- DeliveryMode must be persistence
- copy mysql library into activemq/lib
- Queue: msg will delete from ACTIVEMQ_MSGS once consumer consume message
- Topic: publish msg will not be deleted
- Database Table
    - ACTIVEMQ_MSGS
        + container: destination
        + msgid_prod: producer
        + msg_seq: msgid_prod + msg_seq = JMS Message ID
        + msg: msg binary data
    - ACTIVEMQ_LOCK
        + record master broker
    - ACTIVEMQ_ACKS: 
        + container: destination
        + client_id: subscriber id
- Journal
    + consumer is fast, only persist unprocessed message to DB
    + batch persist to db
```
# JDBC without Journal
<persistenceAdapter>
    # createTablesOnStartup to create table only first time
    <jdbcPersistenceAdapter dataSource="#mysql-ds" createTablesOnStartup="true"/>
</persistenceAdapter>
<bean id="mysql-ds" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost/activemq?relaxAutoCommit=true"/>
    <property name="username" value="root"/>
    <property name="password" value="password"/>
    <property name="poolPreparedStatements" value="true"/>
</bean>

# JDBC with Journal
<persistenceFactory>
 <journalPersistenceAdapterFactory journalLogFiles="5" dataSource="#mysql-ds" useJournal="true" useQuickJournal="true"  dataDirectory="activemq-data" />
</persistenceFactory>
```

## ActiveMQ Cluseter
#### Components
- zookeeper + replicated level db
- master/slave cluster
#### Zookeeper manage cluster
- only one broker expose to outside as master, slave standby
- slave does not allow client connection
- if master down, the slave complete sync earliest become master, 3 node is recommended