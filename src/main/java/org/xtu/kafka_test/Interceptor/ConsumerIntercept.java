package org.xtu.kafka_test.Interceptor;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsumerIntercept implements ConsumerInterceptor<String,String> {


    /**
     * 在消息被消费者接收之前调用，可以对消息进行修改或者过滤
     * @param consumerRecords
     * @return
     */
    @Override
    public ConsumerRecords<String,String> onConsume(ConsumerRecords<String,String> consumerRecords) {
        System.out.println("拦截到的消息： " + consumerRecords);
        return consumerRecords;
    }

    /**
     * 在消费者提交偏移量之前调用，可以对提交的偏移量进行修改或者记录日志
     * @param map
     */
    @Override
    public void onCommit(Map map) {
        System.out.println("提交的偏移量： " + map);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
