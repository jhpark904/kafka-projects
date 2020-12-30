import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.internals.Topic;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

/**
 * Test Consumer with manual commit to receive messages sent from producers
 * Read from specific partitions
 */
public class ConsumerPartitionAssigned {

    /**
     * Read messages from specific partitions
     * with manual commit with minimum batch size 200
     * and write to a file
     * @param args program arguments
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        props.put("group.id", "group3");

        //type of deserializers - match with producer serializers
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        String topic = "numbers";

        //partitions to read from
        TopicPartition[] partitions = {
                new TopicPartition(topic, 2),
                new TopicPartition(topic, 4)
        };

        // consumer object
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.assign(Arrays.asList(partitions));

        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();

        FileWriter fw = null;
        try {
            fw = new FileWriter("/home/jacob/Desktop/numbers.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //commit when the minBatchSize is reached
        try {
            // infinite loop
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    buffer.add(record);
                    String msg = String.format("offset = %d, key = %s, value = %s, partition = %s%n"
                            , record.offset(), record.key(), record.value(), record.partition());
                    System.out.println(msg);
                }

                if (buffer.size() >= minBatchSize) {
                    // write to a file
                    fw.append(buffer.toString());
                    consumer.commitSync();
                    buffer.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        consumer.close();

        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

