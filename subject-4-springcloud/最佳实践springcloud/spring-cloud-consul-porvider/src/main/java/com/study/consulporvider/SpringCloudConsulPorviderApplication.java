package com.study.consulporvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class SpringCloudConsulPorviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsulPorviderApplication.class, args);
    }

    @Value("${test-key}")
    String testKey;

    @GetMapping("user")
    public String getName(@RequestParam("name") String name){
//        System.out.println("打印配置内容：" +testKey);
        return testKey;
    }
}
