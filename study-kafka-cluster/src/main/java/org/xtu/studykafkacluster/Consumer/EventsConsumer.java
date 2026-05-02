package org.xtu.studykafkacluster.Consumer;

import cn.hutool.json.JSONUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.xtu.studykafkacluster.Entity.User;

@Component
public class EventsConsumer {

    @KafkaListener(groupId = "cluster-group" , topics = "cluster-topic")
    public void onEvent11(String Event, ConsumerRecord<Object,Object> consumerRecord) {
        User bean = JSONUtil.toBean(Event, User.class);
        System.out.println( Thread.currentThread().getId() + "消费记录：" + consumerRecord.toString() + bean);
    }
}
