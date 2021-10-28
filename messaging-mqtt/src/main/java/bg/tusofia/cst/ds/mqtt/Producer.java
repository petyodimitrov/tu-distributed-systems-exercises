package bg.tusofia.cst.ds.mqtt;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.nio.charset.StandardCharsets;

/**
 * Message producer.
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost("localhost", 1883);

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        String text = args.length == 0 ? "opa opa" : args[0];

        System.out.println("Sending message to topic '" + Constants.TOPIC + "'...");
        connection.publish(Constants.TOPIC, text.getBytes(StandardCharsets.UTF_8), QoS.AT_MOST_ONCE, false /* do not retain */);
        System.out.println("Sent message.");

        connection.disconnect();
    }
}