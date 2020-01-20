package bg.tusofia.cst.ds.mqtt;

import org.apache.activemq.broker.BrokerService;

/**
 * Class running an embedded ActiveMQ broker.
 */
public class Broker {

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.addConnector("mqtt://0.0.0.0:1883");
        System.out.println("Running embedded Active MQ message broker...");
        broker.start();
    }

}
