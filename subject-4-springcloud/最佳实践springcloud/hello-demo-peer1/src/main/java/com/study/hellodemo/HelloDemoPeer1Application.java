package com.study.hellodemo;


import com.study.hellodemo.accept.StreamClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableBinding(value = {StreamClient.class})
@RemoteApplicationEventScan("com.dn")
@EnableDiscoveryClient
@RefreshScope
public class HelloDemoPeer1Application {

    public static void main(String[] args) {
        SpringApplication.run(HelloDemoPeer1Application.class, args);
    }


    @Value("${zone.name}")
    String name;

    @GetMapping("index")
    public String index(){
        //String str =  "这是服务端2返回的应答";
        return name;
    }


}
