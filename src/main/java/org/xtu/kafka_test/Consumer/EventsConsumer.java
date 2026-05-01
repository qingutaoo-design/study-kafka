package org.xtu.kafka_test.Consumer;

import cn.hutool.json.JSONUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.xtu.kafka_test.Entity.User;

import java.util.List;
import java.util.stream.Collectors;

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
//    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group-id}")
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

//    @KafkaListener(groupId = "${kafka.consumer.group-id}",
//    topicPartitions = {
//            @TopicPartition(topic = "${kafka.topic.name}",
//            partitions = {"0","1","2"},
//            partitionOffsets = {
//                    //指定分区的初始offset，默认是latest,不受消费者组offset的影响，每次重启消费者都会从指定的offset开始消费
//                    @PartitionOffset(partition = "3", initialOffset = "2"),
//                    @PartitionOffset(partition = "4", initialOffset = "2")
//            })
//    })
    public void onEvent5(@Payload String Event,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         Acknowledgment ack) {
        try {
            User user = JSONUtil.toBean(Event, User.class);
            System.out.println("Received event5(user): " + user);
            ack.acknowledge();//手动ack，提交offset
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @KafkaListener(groupId = "${kafka.consumer.group-id}" , topics = "${kafka.topic.name}")
    public void onEvent6(List<ConsumerRecord<String,String>> Events, Acknowledgment ack) {
        try {
            List<User> collect = Events.stream().map(Event -> {
                String value = Event.value();
                User user = JSONUtil.toBean(value, User.class);
                return user;
            }).collect(Collectors.toList());

            System.out.println( "一组消费记录：" + collect + "，数量：" + collect.size());

            ack.acknowledge();//手动ack，提交offset
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(groupId = "${kafka.consumer.group-id}" , topics = "${kafka.topic.name}" ,containerFactory = "kafkaListenerContainerFactory")
    public void onEvent7(String Event) {
            //得转变json格式为对象
            User bean = JSONUtil.toBean(Event, User.class);
            System.out.println( "消费记录：" + bean);

    }

}
