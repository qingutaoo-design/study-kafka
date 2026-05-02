package org.xtu.kafka_test.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.xtu.kafka_test.Interceptor.ConsumerIntercept;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class ConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDserializer;
    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDserializer;


    /**
     * 自定义消费者工厂，会覆盖yml当中spring.kafka.consumer的配置
     * @return
     */
    //当前消费者配置只能消费最新消息
    public Map<String , Object> consumerConfigs(){
        HashMap<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keyDserializer);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueDserializer);
//        添加自定义拦截器
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, ConsumerIntercept.class.getName());
        return props;
    }

    /**
     * 创建ConsumerFactory实例，设置消费者配置
     * @return
     */
//    @Bean
    public ConsumerFactory<String,String> consumerFactory(){
        return new org.springframework.kafka.core.DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * 创建KafkaListenerContainerFactory实例，设置ConsumerFactory
     * @param consumerFactory
     * @return
     */
//    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(ConsumerFactory<String,String> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(consumerFactory);
        return listenerContainerFactory;
    }


}
