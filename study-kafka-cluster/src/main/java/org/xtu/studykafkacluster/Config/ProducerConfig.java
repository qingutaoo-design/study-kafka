package org.xtu.studykafkacluster.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Bean
    public NewTopic newTopic(){
        return new NewTopic("cluster-topic",3,(short) 3);
    }

}
