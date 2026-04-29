package org.xtu.kafka_test;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xtu.kafka_test.Producer.EventsProducer;

@SpringBootTest
class KafkaTestApplicationTests {

    @Resource
    private EventsProducer eventsProducer;



    @Test
    void contextLoads() {
        eventsProducer.sendMessage();
    }

    @Test
    void testMessage2(){
        eventsProducer.sendMessage2();;
    }

    @Test
    void testMessage3() {
        eventsProducer.sendMessage3();
    }
    @Test
    void testMessage4() {
        eventsProducer.sendMessage4();
    }

    @Test
    void testMessage5() {
        eventsProducer.sendMessage5();
    }
    @Test
    void testMessage6() {
        eventsProducer.sendMessage6();
    }
    @Test
    void testMessage7() {
        eventsProducer.sendMessage7();
    }
    @Test
    void testMessage8() {
        for (int i = 0; i < 3; i++) {
            eventsProducer.sendMessage8();
        }

    }
}
