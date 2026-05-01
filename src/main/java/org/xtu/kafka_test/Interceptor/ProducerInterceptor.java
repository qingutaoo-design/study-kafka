package org.xtu.kafka_test.Interceptor;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProducerInterceptor implements org.apache.kafka.clients.producer.ProducerInterceptor<String,Object> {
    @Override
    public ProducerRecord<String, Object> onSend(ProducerRecord<String, Object> producerRecord) {

        System.out.println("拦截到的消息: " + producerRecord.value());

        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if(metadata != null){
            System.out.println("消息发送成功，topic: " + metadata.topic() + ", partition: " + metadata.partition() + ", offset: " + metadata.offset());
        } else {
            System.out.println("消息发送失败，异常信息: " + exception.getMessage());
        }

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
