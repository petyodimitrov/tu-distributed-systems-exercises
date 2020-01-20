package bg.tusofia.cst.ds.mqtt;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import java.nio.charset.StandardCharsets;

/**
 * Message consumer.
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost("localhost", 1883);

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        Topic[] topics = {new Topic(Constants.TOPIC, QoS.AT_MOST_ONCE)};
        connection.subscribe(topics);

        System.out.println("Waiting for message on topic '" + Constants.TOPIC + "'...");
        Message message = connection.receive();

        byte[] payload = message.getPayload();
        message.ack();
        System.out.println("Received message '" + new String(payload, StandardCharsets.UTF_8) + "' on topic '" + message.getTopic() + "'.");

        connection.disconnect();
    }

}