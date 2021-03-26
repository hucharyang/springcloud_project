package com.study.stream.converter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

public class MyMessageConverter extends AbstractMessageConverter {


    public MyMessageConverter() {
        super(new MimeType("application","user"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        //判断当前对象能否转换
        //return User.class.equals(aClass);
        return true;
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        System.out.println("执行->消息to对象的转换");
        Object payLoad = message.getPayload();
        //json---->xml
        return new String((byte[]) payLoad);
    }

    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        System.out.println("执行-->对象to消息的转换");
        //xml --->json
        return payload;
    }
}
