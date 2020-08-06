package com.yufeng.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;


public class SyncProducer {

    public static void main(String[] args) throws Exception{

        final DefaultMQProducer producer = new DefaultMQProducer("test_producer");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();


        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (true) {
                    try {
                        Message msg = new Message(
                                "TopicTest",
                                "TagA",
                                ("Test").getBytes(RemotingHelper.DEFAULT_CHARSET)
                        );
                        SendResult sendResult = producer.send(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        while (true) {
            Thread.sleep(1000);
        }
    }
}
