package com.yufeng.activemqspringbootproducer.service;

import com.yufeng.activemqspringbootproducer.ActivemqSpringbootProducerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * @Auther: daiyu
 * @Date: 6/2/20 23:50
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivemqSpringbootProducerApplication.class)
@WebAppConfiguration
public class QueueProducerTest {

    @Resource
    private QueueProducer queueProducer;

    @Test
    public void testSend() throws Exception{
        queueProducer.produceMsg();
    }
}
