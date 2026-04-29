package org.xtu.kafka_test.Consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EventsConsumer {

    @KafkaListener(topics = "hello-topic", groupId = "helloGroup")
    public void onEvent(@Payload String Event,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header("header1") String value1) {
        System.out.println("Received event: " + Event);

        System.out.println("topic: " + topic + ", partition: " + partition + ", key: " + key + ", value1: " + value1);


    }

}
