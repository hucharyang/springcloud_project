package com.study.customer.controller;

import com.study.customer.stream.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CustomerController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("index")
    public Object getIndex(){
        return restTemplate.getForObject("http://HELLOSERVER/index",String.class,"");
     }



}
