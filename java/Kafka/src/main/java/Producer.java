import org.apache.kafka.clients.producer.*;

import java.util.Date;
import java.util.Properties;

/**
 * Test producer to produces messages to be read from consumers
 */

public class Producer {

    /**
     * create a producer object and send some messages
     * @param args program arguments
     */
    public static void main(String[] args) {

        //properties to pass on to kafka producer
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        //type of serializers
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // acknowledgement
        props.put("acks", "all");

        //producer name
        String clientId = "prod1";
        props.put("client.id", clientId);

        //kafka producer object
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        sendNumbers(producer);
        producer.close();
    }

    /**
     * send numbers from 0-99 as string
     * @param producer producer object
     */
    private static void sendNumbers(KafkaProducer producer) {
        int records = 100;
        String topic = "numbers";

        for (int i = 0; i < records; i++) {
            String msg = String.format("Message %s sent", i);
            System.out.println(msg);
            producer.send(new ProducerRecord<String, String>(topic, i + "", msg));

        }
    }

    /**
     * send 100 formatted string messages to consumers
     * @param producer producer object
     * @param clientId specifies the producer
     */
    private static void sendStrings(KafkaProducer producer, String clientId) {
        String topic = "strings";

        int records = 100;

        try {
            for (int i = 0; i < records; i++) {
                String msg = String.format("Producer %s has sent message %s at %s", clientId, i, new Date());
                System.out.println(msg);
                producer.send(new ProducerRecord<String, String>(topic, i + "", msg));

                // to add delay
                // 3 messages per second
                Thread.sleep(300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
