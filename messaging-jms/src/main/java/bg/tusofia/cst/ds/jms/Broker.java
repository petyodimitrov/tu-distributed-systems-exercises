package bg.tusofia.cst.ds.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

/**
 * Class running an embedded ActiveMQ broker.
 */
public class Broker {

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.addConnector(ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);

        System.out.println("Running embedded Active MQ message broker...");
        broker.start();
    }

}
