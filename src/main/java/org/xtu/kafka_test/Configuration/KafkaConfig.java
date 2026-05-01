package org.xtu.kafka_test.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.xtu.kafka_test.Interceptor.KafkaInterceptor;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;


    @Autowired
    private KafkaInterceptor kafkaInterceptor;

    /**
     * 生产者配置
     * 自定义分区策略
     * @return
     */
    public Map<String , Object> producerConfigs(){
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,valueSerializer);
        //指定分区器为RoundRobinPartitioner,轮询分区策略,默认是DefaultPartitioner,根据key的hash值进行分区
//        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);
        //添加自定义拦截器
//        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, kafkaInterceptor.getClass().getName());
        return props;
    }

    @Bean
    public ProducerFactory<String,Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean(name = {"kafkaTemplate", "kafkaTemplate2"})
    public KafkaTemplate<String , Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }




    //同名topic如果存在则不创建，不存在则创建
    //改变partition只能扩展不能缩小
    @Bean
    public NewTopic newTopic(){
        return new NewTopic("hello-topic", 5, (short) 1);
    }

}
