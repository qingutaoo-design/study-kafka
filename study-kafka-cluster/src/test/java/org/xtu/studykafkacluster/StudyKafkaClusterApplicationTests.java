package org.xtu.studykafkacluster;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xtu.studykafkacluster.Producer.EventsProducer;

@SpringBootTest
class StudyKafkaClusterApplicationTests {

    @Resource
    EventsProducer eventsProducer;

    @Test
    void testMessage () {
        eventsProducer.sendMessage1();
    }
}
