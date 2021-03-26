package com.study.gateway;

import com.study.gateway.filter.JwtCheckGatewayFilterFactory;
import com.study.gateway.filter.JwtCheckSSSS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }

/*    @Bean
    JwtCheckGatewayFilterFactory filterFactory(){
        return new JwtCheckGatewayFilterFactory();
    }*/

    @Bean
    JwtCheckSSSS filterFactory1(){
        return new JwtCheckSSSS();
    }
}
