package com.yufeng.activemqjava.embbedbroker;

import org.apache.activemq.broker.BrokerService;

/**
 * @Auther: daiyu
 * @Date: 6/2/20 20:25
 * @Description:
 */
public class EmbbedBroker {

    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://127.0.0.1:61616");
        brokerService.start();
    }
}
