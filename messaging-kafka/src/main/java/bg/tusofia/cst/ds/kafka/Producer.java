package bg.tusofia.cst.ds.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Message producer.
 */
public class Producer {

    public static void main(String[] args) {
        String topic = args.length == 0 ? Constants.TOPIC : args[0];
        String text = args.length <= 1 ? "opa opa " : args[1];

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", IntegerSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        try (KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 100; i++) {
                System.out.println("Sending message '" + i + "' to topic '" + topic + "'...");
                kafkaProducer.send(new ProducerRecord<>(topic, i, text + i));
                System.out.println("Sent message.");
            }
        }
    }

}