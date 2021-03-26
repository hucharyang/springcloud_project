package com.study.hellodemo.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class MyEvent extends RemoteApplicationEvent {
    private MyEvent(){
    }
    public MyEvent(Object source, String originService){
        super(source,originService,"**");
    }
}
