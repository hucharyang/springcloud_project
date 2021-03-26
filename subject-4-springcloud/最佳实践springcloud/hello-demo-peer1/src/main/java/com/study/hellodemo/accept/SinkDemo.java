package com.study.hellodemo.accept;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class SinkDemo {


    @StreamListener(StreamClient.INPUT)
    public void processVote(Message<?> message) {

        System.out.println("收到数据：" + message.getPayload());
        System.out.println(message.getHeaders());
//        throw new RuntimeException("123123");
    }

}
