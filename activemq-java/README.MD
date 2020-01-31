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
    + Topic: publish-and-subscribe, publisher & subscriber