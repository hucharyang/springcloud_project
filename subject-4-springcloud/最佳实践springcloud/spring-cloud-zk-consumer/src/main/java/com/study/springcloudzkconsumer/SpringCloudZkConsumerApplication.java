package com.study.springcloudzkconsumer;

import com.study.springcloudzkconsumer.service.ConsumerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableFeignClients
@EnableDiscoveryClient
public class SpringCloudZkConsumerApplication {
    @Autowired
    ConsumerClient client;

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZkConsumerApplication.class, args);
    }

    @Value("${test}")
    String test;

    @GetMapping("get-user")
    String getUser(@RequestParam String name){
        System.out.println(test);
        return test;
    }
}
