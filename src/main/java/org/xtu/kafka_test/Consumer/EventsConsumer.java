package org.xtu.kafka_test.Consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EventsConsumer {

    @KafkaListener(topics = "hello-topic", groupId = "helloGroup")
    public void onEvent(@Payload String Event){
        System.out.println("Received event: " + Event);


    }

}
