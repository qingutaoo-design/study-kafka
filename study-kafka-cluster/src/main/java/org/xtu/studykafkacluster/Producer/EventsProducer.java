package org.xtu.studykafkacluster.Producer;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.xtu.studykafkacluster.Entity.User;

@Component
public class EventsProducer {

    @Resource
    KafkaTemplate kafkaTemplate;

    public void sendMessage1(){
        for (int i = 0; i < 3; i++) {
            User user = User.builder().age(18).name("xtu").phone("123456789").id(i).build();
            String userJSON = JSONUtil.toJsonStr(user);
            //指定key，根据key的hash值进行分区
            kafkaTemplate.send("cluster-topic","" + i,userJSON);
        }
    }
}
