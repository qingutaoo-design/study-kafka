package org.xtu.kafka_test.Consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventsConsumer {

    @KafkaListener(topics = "hello-topic", groupId = "hello-group2")
    public void onEvent(String Event){
        System.out.println("Received event: " + Event);


    }

}
