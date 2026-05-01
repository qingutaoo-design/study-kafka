package org.xtu.kafka_test.Consumer;

import cn.hutool.json.JSONUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.xtu.kafka_test.Entity.User;

@Component
public class EventsConsumer {

//    @KafkaListener(topics = "hello-topic", groupId = "helloGroup")
    public void onEvent(@Payload String Event,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header("header1") String value1,
                        ConsumerRecord consumerRecord) {
        System.out.println("Received event: " + Event);

        System.out.println("topic: " + topic + ", partition: " + partition + ", key: " + key + ", value1: " + value1);

        System.out.println("ConsumerRecord: " + consumerRecord.toString());

    }

//    @KafkaListener(topics = "hello-topic", groupId = "helloGroup")
    public void onEvent2(String Event,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                         ConsumerRecord consumerRecord) {
        User user = JSONUtil.toBean(Event, User.class);
        System.out.println("Received event(user): " + user);

        System.out.println("topic: " + topic + ", partition: " + partition);

        System.out.println("ConsumerRecord: " + consumerRecord.toString());

    }

//    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group-id}")
    public void onEvent3(String Event,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                         ConsumerRecord consumerRecord) {
        User user = JSONUtil.toBean(Event, User.class);
        System.out.println("Received event3(user): " + user);

        System.out.println("topic: " + topic + ", partition: " + partition);

        System.out.println("ConsumerRecord: " + consumerRecord.toString());

    }

    /**
     * 开启手动ack
     * @param Event
     * @param topic
     * @param partition
     * @param consumerRecord
     * @param ack
     */
    //手动提交offset,默认是自动提交的，如果消费者在处理消息时发生异常，可能会导致消息丢失，因为offset已经提交了，
    // 但是消息还没有被处理完，所以可以通过手动提交offset来解决这个问题
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group-id}")
    public void onEvent4(String Event,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                         ConsumerRecord consumerRecord,
                         Acknowledgment ack) {
        try {
            User user = JSONUtil.toBean(Event, User.class);
            System.out.println("Received event4(user): " + user);
            System.out.println("topic: " + topic + ", partition: " + partition);
            System.out.println("ConsumerRecord: " + consumerRecord.toString());
            ack.acknowledge();//手动ack，提交offset
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
