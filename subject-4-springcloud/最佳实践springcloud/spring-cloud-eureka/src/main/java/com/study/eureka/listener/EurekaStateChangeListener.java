package com.study.eureka.listener;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaStateChangeListener {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event){//监听到服务的下线
        System.err.println(event.getServerId()+"\t"+event.getAppName()+"已下线");
        //。。。发送短信
        System.out.println("发送短信.");
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event){//监听到服务的下线
        System.err.println(event.getInstanceInfo().getAppName()+"已注册");
        //。。。发送短信
        System.out.println("发送短信.");
    }


    @EventListener
    public void listen(EurekaRegistryAvailableEvent event){//监听到服务的下线
        System.err.println("服务中心启动~~!");

    }




}
