package org.xtu.kafka_test.Producer;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.xtu.kafka_test.Entity.User;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class EventsProducer {
    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    @Resource
    private KafkaTemplate<String,Object> kafkaTemplate2;


    public void sendMessage(){
        kafkaTemplate.send("hello-topic","hello-event");
    }

    public void sendMessage2(){

        Message<String> message = MessageBuilder.withPayload("hello-event2")
                //指定topic,在消息头当中
                .setHeader(KafkaHeaders.TOPIC,"hello-topic")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendMessage3(){
        Headers head = new RecordHeaders();
        head.add("header1","value1".getBytes());
        head.add("header2","value2".getBytes());

        ProducerRecord<String,String> record = new ProducerRecord<>(
                "hello-topic",
                0,
                System.currentTimeMillis(),
                "hello-key",
                "hello-event3",
                head
        );

        kafkaTemplate.send(record);
    }

    public void sendMessage4(){
        //发送到默认topic,默认分区0,默认key为k4,默认时间戳为当前时间
        //默认topic在application.properties当中配置
        kafkaTemplate.sendDefault(0,System.currentTimeMillis(),"k4","hello-event4");
    }

    public void sendMessage5(){
        //默认topic在application.properties当中配置
        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.sendDefault(0, System.currentTimeMillis(), "k5", "hello-event5");

        //同步阻塞拿返回结果
        try {
            SendResult<String, String> result = future.get();
            ProducerRecord<String, String> producerRecord = result.getProducerRecord();
            RecordMetadata recordMetadata = result.getRecordMetadata();
            System.out.println("metadate: " + recordMetadata.toString());
            System.out.println("producerRecord: " + producerRecord.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage6(){
        //默认topic在application.properties当中配置
        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.sendDefault(0, System.currentTimeMillis(), "k6", "hello-event6");

        //异步拿返回结果

        future.thenAccept(result -> {
            ProducerRecord<String, String> producerRecord = result.getProducerRecord();
            RecordMetadata recordMetadata = result.getRecordMetadata();
            System.out.println("metadate: " + recordMetadata.toString());
            System.out.println("producerRecord: " + producerRecord.toString());
        }).exceptionally(ex ->{
            ex.printStackTrace();
            return null;
        });

    }

    public void sendMessage7(){
        User user = User.builder().age(18).name("xtu").phone("123456789").build();

        kafkaTemplate2.sendDefault(null,System.currentTimeMillis(),"k7",user);

    }

    public void sendMessage8(){
        User user = User.builder().age(18).name("xtu").phone("123456789").build();


            kafkaTemplate2.send("hello-topic2",user);



    }

    public void sendMessage9(){
        User user = User.builder().age(18).name("xtu").phone("123456789").build();

        String userJSON = JSONUtil.toJsonStr(user);

        kafkaTemplate.send("hello-topic",userJSON);



    }
}
