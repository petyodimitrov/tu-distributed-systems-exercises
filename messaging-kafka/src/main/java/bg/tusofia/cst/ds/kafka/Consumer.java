package bg.tusofia.cst.ds.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Message consumer.
 */
public class Consumer {

    public static void main(String[] args) {
        String topic = args.length == 0 ? Constants.TOPIC : args[0];
        String group = args.length <= 1 ? Constants.GROUP : args[1];

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", IntegerDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("group.id", group); // this can be changed to show parallelism

        List<String> topics = new ArrayList<>();
        topics.add(topic);

        try (KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties)) {
            kafkaConsumer.subscribe(topics);
            System.out.println("Waiting for message on topic '" + topic + "'...");

            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("Topic: '%s', Partition: '%d', Value: '%s'", record.topic(), record.partition(), record.value()));
                }
            }
        }
    }

}