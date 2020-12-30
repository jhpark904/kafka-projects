import org.apache.kafka.clients.consumer.*;

import java.time.Duration;
import java.util.*;

/**
 * Test Consumer with auto commit to receive messages sent from producers
 */
public class ConsumerAutoCommit {

    /**
     * create a consumer object that automatically commits every second
     * and read messages sent
     * @param args program arguments
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        props.put("group.id", "group1");

        //type of deserializers - match with producer serializers
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // auto commit every second
        props.put("auto.commit.interval.ms", "1000");
        props.put("enable.auto.commit", "true");

        String[] topics = {"numbers"};

        // consumer object
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(topics));

        try {
            // infinite loop
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String msg = String.format("offset = %d, key = %s, value = %s, partition = %s%n"
                            , record.offset(), record.key(), record.value(), record.partition());
                    System.out.println(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumer.close();
    }

}
